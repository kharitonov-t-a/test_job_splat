package banner.service.implementations;

import banner.dao.interfaces.AuditDao;
import banner.dao.interfaces.BannerDao;
import banner.dao.interfaces.UserDao;
import banner.model.User;
import banner.model.enums.UserRoles;
import banner.service.GenericServiceImpl;
import banner.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Integer, UserDao> implements UserService {

    @Autowired
    AuditDao auditDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByName(String username) {
        User user = dao.findUserByUsername(username);
        return user;
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRoles.ROLE_MANAGER);
        return dao.create(user);
    }

    @Override
    public User update(User user) {
        if(user.getPassword() != null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }else{
            User currentUser = dao.findById(user.getId());
            user.setPassword(currentUser.getPassword());
        }
        user = dao.update(user);
        user.setPassword(null);
        return dao.update(user);
    }

    /**
     * clean audit if banners and user deleted
     * @param id
     */
    @Override
    public void delete(Integer id) {
        dao.delete(id);
        auditDao.cleanAudit();
    }
}