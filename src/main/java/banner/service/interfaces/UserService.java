package banner.service.interfaces;

import banner.model.User;
import banner.service.GenericService;

import java.util.List;

public interface UserService extends GenericService<User, Integer> {

    User getUserByName(String username);
    List<User> findAllWithoutPassword();

}
