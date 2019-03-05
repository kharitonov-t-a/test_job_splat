package banner.service;

import banner.dao.interfaces.UserDao;
import banner.model.User;
import banner.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getUser(String username) {
        User user = userDao.findUserByUsername(username);
//                new User();
//        user.setUsername(username);
//        user.setPassword("$2a$10$cYLM.qoXpeAzcZhJ3oXRLu9Slkb61LHyWW5qJ4QKvHEMhaxZ5qCPi");

        return user;
    }

}