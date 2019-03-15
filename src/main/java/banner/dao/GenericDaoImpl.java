package banner.dao;

import banner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public abstract class GenericDaoImpl <T, PK extends Serializable, Mapper extends GenericMapper<T>> implements GenericDao<T, PK> {//implements RowMapper<T>


    /** SELECT * FROM T //ORDER BY priority// */
    protected abstract String getSELECT_ALL_SQL();

    /** SELECT * FROM T WHERE id = ? */
    protected abstract String getSELECT_BY_ID_SQL();

    /** DELETE FROM T WHERE id = ? */
    protected abstract String getTOTAL_DELETE_SQL();

    /** UPDATE T SET activity = ? WHERE id = ? */
    protected abstract String getUPDATE_ACTIVITY_SQL();

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected Mapper mapper;

    @Override
    public List<T> findAll() {
        return jdbcTemplate.query(getSELECT_ALL_SQL(), mapper);
    }

    @Override
    public T findById(PK id) {
        return this.jdbcTemplate.queryForObject(getSELECT_BY_ID_SQL(), new Object[] { id }, mapper);
    }

    @Override
    public void delete(PK id) {//SQLException
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(getTOTAL_DELETE_SQL(), Statement.NO_GENERATED_KEYS);
            ps.setInt(1, (Integer) id);
            return ps;
        });
    }

    @Override
    public boolean switchActivity(PK id, boolean activity) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(getUPDATE_ACTIVITY_SQL(), Statement.NO_GENERATED_KEYS);
            ps.setInt(1, activity?1:0);
            ps.setInt(2, (Integer) id);

            return ps;
        });
        return true;
    }


}

