package banner.dao;

import banner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public abstract class GenericDaoImpl <T, PK extends Serializable, Mapper extends GenericMapper<T>> implements GenericDao<T, PK> {//implements RowMapper<T>

    private String nameEntity;
//    @SuppressWarnings("unchecked")
    public GenericDaoImpl(){
        Class<T> genericType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.nameEntity = genericType.getSimpleName();
    }

    /** SELECT * FROM T ORDER BY id */
    protected String getSELECT_ALL_SQL(){
        return "SELECT * FROM " + this.nameEntity + " ORDER BY id";
    }

    /** SELECT * FROM T WHERE id = ? */
    protected String getSELECT_BY_ID_SQL(){
        return "SELECT * FROM " + this.nameEntity + " WHERE id = ?";
    }

    /** DELETE FROM T WHERE id = ? */
    protected String getTOTAL_DELETE_SQL(){
        return "DELETE FROM " + this.nameEntity + " WHERE id = ?";
    }

    /** UPDATE T SET activity = ? WHERE id = ? */
    protected String getUPDATE_ACTIVITY_SQL(){
        return "UPDATE " + this.nameEntity + " SET activity = ? WHERE id = ?";
    }

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private DataSource dataSource;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public Mapper mapper;

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

