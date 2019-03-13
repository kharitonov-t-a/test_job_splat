package banner.dao.implementations;

import banner.dao.interfaces.UserDao;
import banner.model.User;
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
public class UserDaoImpl implements UserDao {

    private final String INSERT_SQL = "INSERT INTO User(username, password) values(?,?)";
    private final String SELECT_ALL_SQL = "select id, username, password from User";
    private final String SELECT_SQL_BY_USERNAME = "select * from User where username = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User create(final User user) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                return ps;
            }
        }, holder);

        int newUserId = holder.getKey().intValue();
        user.setId(newUserId);
        return user;
    }

    @Override
    public List findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new UserMapper());
    }

    @Override
    public User findUserByUsername(String username) {
        return (User) jdbcTemplate.queryForObject(SELECT_SQL_BY_USERNAME, new Object[] { username }, new UserMapper());
    }

    class UserMapper implements RowMapper {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}


