package banner.dao.interfaces;

import banner.model.User;

import java.util.List;

public interface UserDao {
    public User create(final User user);
    public List findAll();
    public User findUserByUsername(String username);
}
