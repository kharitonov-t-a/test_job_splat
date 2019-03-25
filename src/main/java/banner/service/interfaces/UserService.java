package banner.service.interfaces;

import banner.model.User;
import banner.service.GenericService;

import java.util.List;

public interface UserService extends GenericService<User, Integer> {

    /**
     * @param username
     * @return
     */
    User getUserByName(String username);

    /**
     * @return
     */
    List<User> findAllWithoutPassword();

}
