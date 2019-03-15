package banner.service.interfaces;

import banner.model.User;
import banner.service.GenericService;

public interface UserService extends GenericService<User, Integer> {

    User getUserByName(String username);

}
