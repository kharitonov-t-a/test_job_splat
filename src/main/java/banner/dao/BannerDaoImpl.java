package banner.dao;

import banner.dao.interfaces.BannerDao;
import banner.model.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class BannerDaoImpl implements BannerDao {

    private final String INSERT_SQL =
            " INSERT INTO Banners(imgSrc, width, height, targetUrl, langId, priority, activity) " +
                " VALUES(?,?,?,?,?,?,?)";// SELECT  MAX( priority ) + 1 mysql_insert_id()
    private final String SELECT_ALL_SQL = "SELECT * FROM Banners ORDER BY priority";
    private final String SELECT_IMAGE = "SELECT imgSrc FROM Banners WHERE  id = ?";
    private final String SELECT_MAX_PRIORITY = "SELECT MAX(priority) FROM Banners";
    private final String UPDATE_SQL =
            " UPDATE Banners " +
            " SET" +
            " imgSrc = ?, width = ?, height = ?, targetUrl = ?, langId = ?, priority = ?, activity = ?" +
            " WHERE id = ?";
    private final String UPDATE_PRIORITY_SQL =
            " UPDATE Banners " +
            " SET" +
            " priority = ?" +
            " WHERE id = ?";
    private final String DISABLE_ACTIVITY_SQL =
            " UPDATE Banners " +
            " SET" +
            " activity = 0" +
            " WHERE id = ?";
    private final String TOTAL_DELETE_SQL = "DELETE FROM Banners WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Banner> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new BannerMapper());
    }


    @Override
    public String getBannerImage(Integer id) {
        return (String)jdbcTemplate.queryForObject(
                SELECT_IMAGE, new Object[] { id }, String.class);
    }

    @Override
    public Integer getMaxPriority() {
        return (Integer)jdbcTemplate.queryForObject(
                SELECT_MAX_PRIORITY, Integer.class);
    }

    @Override
    public Banner create(Banner banner) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, banner.getImgSrc());
                ps.setInt(2, banner.getWidth());
                ps.setInt(3, banner.getHeight());
                ps.setString(4, banner.getTargetUrl());
                ps.setInt(5, banner.getLangId());
                ps.setInt(6, banner.getPriority());
                ps.setInt(7, banner.isActivity()?1:0);
                return ps;
            }
        }, holder);

        int newUserId = holder.getKey().intValue();
        banner.setId(newUserId);
        return banner;
    }

    @Override
    public Banner update(Banner banner) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, Statement.SUCCESS_NO_INFO);
                ps.setString(1, banner.getImgSrc());
                ps.setInt(2, banner.getWidth());
                ps.setInt(3, banner.getHeight());
                ps.setString(4, banner.getTargetUrl());
                ps.setInt(5, banner.getLangId());
                ps.setInt(6, banner.getPriority());
                ps.setInt(7, banner.isActivity()?1:0);
                ps.setInt(8, banner.getId());
                return ps;
            }
        });
        return banner;
    }

    @Override
    public void updatePriority(Banner banner) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(UPDATE_PRIORITY_SQL, Statement.SUCCESS_NO_INFO);
                ps.setInt(1, banner.getPriority());
                ps.setInt(2, banner.getId());
                return ps;
            }
        });
    }

    @Override
    public boolean disableActivity(Integer id) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(DISABLE_ACTIVITY_SQL, Statement.SUCCESS_NO_INFO);
                ps.setInt(1, id);
                return ps;
            }
        });
        return true;
    }
    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(TOTAL_DELETE_SQL, Statement.SUCCESS_NO_INFO);
                ps.setInt(1, id);
                return ps;
            }
        });
    }



}
class BannerMapper implements RowMapper {

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