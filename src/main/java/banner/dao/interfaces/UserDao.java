package banner.dao.interfaces;

import banner.dao.GenericDao;
import banner.model.User;

import java.util.List;

public interface UserDao  extends GenericDao<User, Integer> {

    /**
     * @param username
     * @return
     */
    User findUserByUsername(String username);
}
