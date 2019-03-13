package banner.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class GenericDaoImpl <T, PK extends Serializable> implements GenericDao<T, PK> {

//    private String SELECT_ALL_SQL;
    protected abstract String getINSERT_SQL();
//    private String INSERT_SQL;
//    private String UPDATE_SQL;
//    private String DISABLE_ACTIVITY_SQL;
//    private String TOTAL_DELETE_SQL;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public T create(T newInstance) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(getINSERT_SQL(), Statement.RETURN_GENERATED_KEYS);
            setCreatePreparedStatement(ps, newInstance);
            return ps;
        }, holder);

        int newId = holder.getKey().intValue();
        setId(newInstance, newId);
        return newInstance;
    }

    protected abstract void setId(T newInstance, int newId);
    protected abstract void setCreatePreparedStatement(PreparedStatement ps, T newInstance) throws SQLException;

//    @Override
//    public T update(T transientObject) {
//        return null;
//    }
//
//    @Override
//    public void delete(PK id) {
//
//    }
//
//    @Override
//    public boolean disable(PK id) {
//        return false;
//    }
//
//    @Override
//    public List<T> findAll() {
//        return null;
//    }
}
