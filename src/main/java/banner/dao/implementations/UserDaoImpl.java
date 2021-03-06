package banner.dao.implementations;

import banner.dao.GenericDaoImpl;
import banner.dao.GenericMapper;
import banner.dao.interfaces.UserDao;
import banner.model.User;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Integer, GenericMapper<User>> implements UserDao {

    private final String INSERT_SQL =
            "INSERT INTO User(username, password, role, activity) values(?,?,?,?)";

    private final String UPDATE_SQL =
            "UPDATE User SET username = ?, password = ?, role = ?, activity = ? WHERE id = ?";

    private final String SELECT_BY_USERNAME_SQL =
            "select * from User where username = ?";


    @Override
    public User create(final User user) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole().toString());
                ps.setInt(4, user.getActivity()?1:0);
                return ps;
            }
        }, holder);

        int newUserId = holder.getKey().intValue();
        user.setId(newUserId);
        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SQL, Statement.NO_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());
            ps.setInt(4, user.getActivity()?1:0);
            ps.setInt(5, user.getId());
            return ps;
        });
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return jdbcTemplate.queryForObject(SELECT_BY_USERNAME_SQL, new Object[] { username }, mapper);
    }

}


