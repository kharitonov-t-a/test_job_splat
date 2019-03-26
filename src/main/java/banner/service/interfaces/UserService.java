package banner.service.interfaces;

import banner.model.User;
import banner.service.GenericService;

import java.util.List;

public interface UserService extends GenericService<User, Integer> {

    /**
     * Get user by name
     * @param username name searched user
     * @return searched user
     */
    User getUserByName(String username);

    /**
     * Get all users and return them with emptied password
     * @return user list
     */
    List<User> findAllWithoutPassword();

}
