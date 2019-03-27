package banner.dao.implementations;

import banner.dao.GenericDaoImpl;
import banner.dao.GenericMapper;
import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class BannerDaoImpl extends GenericDaoImpl<Banner, Integer, GenericMapper<Banner>> implements BannerDao {

    @Override protected String getSELECT_ALL_SQL() {
        return "SELECT * FROM Banner " +
                "LEFT JOIN Locale ON Banner.langId = Locale.id " +
                "WHERE Locale.activity = 1 ORDER BY priority";
    }

    private final String INSERT_SQL =
            " INSERT INTO Banner(imgSrc, width, height, targetUrl, langId, priority, activity) " +
                " VALUES(?,?,?,?,?,?,?)";

    private final String UPDATE_SQL =
            " UPDATE Banner  SET" +
                    " imgSrc = ?, width = ?, height = ?, targetUrl = ?, langId = ?, priority = ?, activity = ?" +
                    " WHERE id = ?";

    private final String SELECT_IMAGE =
            "SELECT imgSrc FROM Banner WHERE id = ?";

    private final String SELECT_MAX_PRIORITY =
            "SELECT MAX(priority) FROM Banner";

    private final String UPDATE_PRIORITY_SQL =
            " UPDATE Banner SET priority = ? WHERE id = ?";

    private final String UPDATE_ACTIVITY_BY_LOCALE_SQL =
            " UPDATE Banner SET activity = ? WHERE langId = ?";

    private final String CLEAN_BANNERS_SQL =
            " DELETE FROM Banner" +
                    " WHERE langId NOT IN (SELECT id FROM Locale)";


    @Override
    public Banner create(Banner banner) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, banner.getImgSrc());
            ps.setInt(2, banner.getWidth());
            ps.setInt(3, banner.getHeight());
            ps.setString(4, banner.getTargetUrl());
            ps.setInt(5, banner.getLangId());
            ps.setInt(6, banner.getPriority());
            ps.setInt(7, banner.getActivity()?1:0);
            return ps;
        }, holder);

        int newId = holder.getKey().intValue();
        banner.setId(newId);
        return banner;
    }

    @Override
    public Banner update(Banner banner) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, Statement.NO_GENERATED_KEYS);
            ps.setString(1, banner.getImgSrc());
            ps.setInt(2, banner.getWidth());
            ps.setInt(3, banner.getHeight());
            ps.setString(4, banner.getTargetUrl());
            ps.setInt(5, banner.getLangId());
            ps.setInt(6, banner.getPriority());
            ps.setInt(7, banner.getActivity()?1:0);
            ps.setInt(8, banner.getId());
            return ps;
        });
        return banner;
    }

    @Override
    public String getBannerImage(Integer id) {
        return jdbcTemplate.queryForObject(
                SELECT_IMAGE, new Object[] { id }, String.class);
    }

    @Override
    public Integer getMaxPriority() {
        return jdbcTemplate.queryForObject(
                SELECT_MAX_PRIORITY, Integer.class);
    }

    @Override
    public void updatePriority(Banner banner) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_PRIORITY_SQL, Statement.NO_GENERATED_KEYS);
            ps.setInt(1, banner.getPriority());
            ps.setInt(2, banner.getId());
            return ps;
        });
    }

    @Override
    public void switchActivityByLocale(Integer localeId, boolean newActivityState) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ACTIVITY_BY_LOCALE_SQL, Statement.NO_GENERATED_KEYS);
            ps.setInt(1, newActivityState?1:0);
            ps.setInt(2, localeId);
            return ps;
        });
    }

    @Override
    public void cleanBanners() {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CLEAN_BANNERS_SQL, Statement.NO_GENERATED_KEYS);
            return ps;
        });
    }
}
