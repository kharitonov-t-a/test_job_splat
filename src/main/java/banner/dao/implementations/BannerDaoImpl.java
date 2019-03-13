package banner.dao.implementations;

import banner.dao.GenericDaoImpl;
import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class BannerDaoImpl extends GenericDaoImpl<Banner, Integer> implements BannerDao {

    private final String INSERT_SQL =
            " INSERT INTO Banner(imgSrc, width, height, targetUrl, langId, priority, activity) " +
                " VALUES(?,?,?,?,?,?,?)";

    private final String SELECT_ALL_SQL =
            "SELECT * FROM Banner ORDER BY priority";

    private final String SELECT_BY_ID_SQL =
            "SELECT * FROM Banner WHERE id = ?";

    private final String SELECT_IMAGE =
            "SELECT imgSrc FROM Banner WHERE id = ?";

    private final String SELECT_MAX_PRIORITY =
            "SELECT MAX(priority) FROM Banner";

    private final String UPDATE_SQL =
            " UPDATE Banner " +
            " SET" +
            " imgSrc = ?, width = ?, height = ?, targetUrl = ?, langId = ?, priority = ?, activity = ?" +
            " WHERE id = ?";

    private final String UPDATE_PRIORITY_SQL =
            " UPDATE Banner " +
            " SET" +
            " priority = ?" +
            " WHERE id = ?";

    private final String DISABLE_ACTIVITY_SQL =
            " UPDATE Banner " +
            " SET" +
            " activity = 0" +
            " WHERE id = ?";

    private final String TOTAL_DELETE_SQL =
            "DELETE FROM Banner WHERE id = ?";

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Banner> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new BannerMapper());
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


//    @Override
//    public Banner create(Banner banner) {
//        KeyHolder holder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, banner.getImgSrc());
//            ps.setInt(2, banner.getWidth());
//            ps.setInt(3, banner.getHeight());
//            ps.setString(4, banner.getTargetUrl());
//            ps.setInt(5, banner.getLangId());
//            ps.setInt(6, banner.getPriority());
//            ps.setInt(7, banner.getActivity()?1:0);
//            return ps;
//        }, holder);
//
//        int newId = holder.getKey().intValue();
//        banner.setId(newId);
//        return banner;
//    }


    @Override
    protected void setId(Banner banner, int newId) {
        banner.setId(newId);
    }

    @Override
    protected void setCreatePreparedStatement(PreparedStatement ps, Banner banner) throws SQLException {
        ps.setString(1, banner.getImgSrc());
        ps.setInt(2, banner.getWidth());
        ps.setInt(3, banner.getHeight());
        ps.setString(4, banner.getTargetUrl());
        ps.setInt(5, banner.getLangId());
        ps.setInt(6, banner.getPriority());
        ps.setInt(7, banner.getActivity()?1:0);
    }

    @Override
    protected String getINSERT_SQL() {
        return INSERT_SQL;
    }

    @Override
    public Banner findById(Integer id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[] { id }, new BannerMapper());
    }

    @Override
    public Banner update(Banner banner) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, Statement.SUCCESS_NO_INFO);
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
    public void updatePriority(Banner banner) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_PRIORITY_SQL, Statement.SUCCESS_NO_INFO);
            ps.setInt(1, banner.getPriority());
            ps.setInt(2, banner.getId());
            return ps;
        });
    }

    @Override
    public boolean disable(Integer id) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(DISABLE_ACTIVITY_SQL, Statement.SUCCESS_NO_INFO);
            ps.setInt(1, id);
            return ps;
        });
        return true;
    }
    @Override
    public void delete(Integer id) {//SQLException
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(TOTAL_DELETE_SQL, Statement.SUCCESS_NO_INFO);
            ps.setInt(1, id);
            return ps;
        });
    }

    class BannerMapper implements RowMapper<Banner> {
        @Override
        public Banner mapRow(ResultSet rs, int rowNum) throws SQLException {
            Banner banner = new Banner();
            banner.setId(rs.getInt("id"));
            banner.setImgSrc(rs.getString("imgSrc"));
            banner.setWidth(rs.getInt("width"));
            banner.setHeight(rs.getInt("height"));
            banner.setTargetUrl(rs.getString("targetUrl"));
            banner.setLangId(rs.getInt("langId"));
            banner.setPriority(rs.getInt("priority"));
            banner.setActivity(rs.getInt("activity") != 0);
            return banner;
        }
    }
}
