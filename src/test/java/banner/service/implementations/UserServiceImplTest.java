package banner.service.implementations;

import banner.dao.implementations.AuditDaoImpl;
import banner.dao.implementations.UserDaoImpl;
import banner.dao.mappers.AuditMapper;
import banner.dao.mappers.UserMapper;
import banner.model.Audit;
import banner.model.User;
import banner.model.enums.UserRoles;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    private UserDaoImpl userDao;
    private UserServiceImpl userService;
    EmbeddedDatabase db;
    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sqlScripts/createUserTable")
                .addScript("sqlScripts/createAuditTable")
                .addScript("sqlScripts/createBannerTable")
                .addScript("sqlScripts/fillAuditTableForUserTest")
                .build();
        userDao = new UserDaoImpl();
        userDao.setDataSource(db);
        userDao.postConstruct();
        userDao.mapper = new UserMapper();
        AuditDaoImpl auditDao = new AuditDaoImpl();
        auditDao.setDataSource(db);
        auditDao.postConstruct();
        auditDao.mapper = new AuditMapper();

        userService = new UserServiceImpl();
        userService.dao = userDao;
        userService.auditDao = auditDao;

        userService.passwordEncoder = new BCryptPasswordEncoder();
    }

    @After
    public void tearDown() throws Exception {
        db.shutdown();
    }

    @Test
    public void getUserByName() {
    }

    @Test
    public void create() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(UserRoles.ROLE_ADMIN);
        user.setActivity(true);


        user = userService.create(user);
        System.out.println("Save User: " + user.toString());
        List<User> loadedUsers = userService.findAll();
        System.out.println("Loaded User: " + loadedUsers.toString());

        System.out.println("Loaded Users: " + loadedUsers.toString());
        for (User loadedUser : loadedUsers) {
            Assert.assertEquals("username", loadedUser.getUsername());
            Assert.assertNotNull(loadedUser.getPassword());
            Assert.assertEquals(UserRoles.ROLE_MANAGER, loadedUser.getRole());
            Assert.assertTrue(loadedUser.getActivity());
        }
    }

    @Test
    public void update() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(UserRoles.ROLE_ADMIN);
        user.setActivity(true);


        User createdUser = userService.create(user);
        System.out.println("Save User: " + user.toString());

        createdUser.setRole(UserRoles.ROLE_ADMIN);
        createdUser.setPassword("newpassword");
        createdUser.setUsername("newusername");

        User updatedUser = userService.update(createdUser);

        Assert.assertEquals("newusername", updatedUser.getUsername());
        Assert.assertNotNull(updatedUser.getPassword());
        Assert.assertEquals(UserRoles.ROLE_ADMIN, updatedUser.getRole());
    }

    @Test
    public void delete() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(UserRoles.ROLE_ADMIN);
        user.setActivity(true);

        List<Audit> auditList = userService.auditDao.findAll();
        Assert.assertEquals(auditList.size(), 1);

        User createdUser = userService.create(user);
        System.out.println("Save User: " + user.toString());

        User loadedUser = userService.getUserByName("username");
        Assert.assertEquals(1, (int) loadedUser.getId());
        Assert.assertEquals("username", loadedUser.getUsername());

        userService.delete(loadedUser.getId());

        //если запись аудита не ссылается ни на юзера, ни на баннер, то чистим
        auditList = userService.auditDao.findAll();
        Assert.assertEquals(auditList.size(), 0);

        try {
            userService.getUserByName("username");
        }catch (EmptyResultDataAccessException ex){
            Assert.assertTrue(true);
        }
    }
}