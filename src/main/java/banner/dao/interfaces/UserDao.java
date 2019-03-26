package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.User;

import java.util.List;

public interface UserDao  extends GenericDao<User, Integer> {

    /**
     * Get user by user name
     * @param username user name
     * @return model user
     */
    User findUserByUsername(String username);
}
