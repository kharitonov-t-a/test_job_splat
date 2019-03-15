package banner.dao.mappers;

import banner.dao.GenericMapper;
import banner.model.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
class UserMapper implements GenericMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setActivity(rs.getInt("activity") != 0);
        return user;
    }
}
