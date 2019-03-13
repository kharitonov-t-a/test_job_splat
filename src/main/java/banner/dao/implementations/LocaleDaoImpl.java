package banner.dao.implementations;

import banner.dao.GenericDaoImpl;
import banner.dao.interfaces.LocaleDao;
import banner.model.Banner;
import banner.model.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class LocaleDaoImpl extends GenericDaoImpl<Locale, Integer> implements LocaleDao {

    private final String SELECT_ALL_SQL =
            "SELECT * FROM Locale ORDER BY id";

    private final String SELECT_BY_ID_SQL =
            "SELECT * FROM Locale WHERE id = ?";


    private final String INSERT_SQL =
            "INSERT INTO Locale(name, activity) VALUES(?,?)";

    private final String UPDATE_SQL =
            "UPDATE Locale SET name = ?, activity = ? WHERE id = ?";

    private final String DISABLE_ACTIVITY_SQL =
            "UPDATE Locale SET activity = 0 WHERE id = ?";

    private final String TOTAL_DELETE_SQL =
            "DELETE FROM Locale WHERE id = ?";

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Locale> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new LocaleMapper());
    }

    @Override
    public Locale findById(Integer id) {
        return this.jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[] { id }, new LocaleMapper());
    }

//    @Override
//    public Locale create(Locale locale) {
//        KeyHolder holder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, locale.getName());
//            ps.setInt(2, locale.getActivity()?1:0);
//            return ps;
//        }, holder);
//
//        int newId = holder.getKey().intValue();
//        locale.setId(newId);
//        return locale;
//    }

    @Override
    protected String getINSERT_SQL() {
        return INSERT_SQL;
    }

    @Override
    protected void setId(Locale locale, int newId) {
        locale.setId(newId);
    }

    @Override
    protected void setCreatePreparedStatement(PreparedStatement ps, Locale locale) throws SQLException {
        ps.setString(1, locale.getName());
        ps.setInt(2, locale.getActivity()?1:0);
    }

    @Override
    public Locale update(Locale locale) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, Statement.SUCCESS_NO_INFO);
            ps.setString(1, locale.getName());
            ps.setInt(2, locale.getActivity()?1:0);
            ps.setInt(3, locale.getId());
            return ps;
        });
        return locale;
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

    class LocaleMapper implements RowMapper<Locale> {

        @Override
        public Locale mapRow(ResultSet rs, int rowNum) throws SQLException {
            Locale locale = new Locale();
            locale.setId(rs.getInt("id"));
            locale.setName(rs.getString("name"));
            locale.setActivity(rs.getInt("activity") != 0);
            return locale;
        }
    }
}
