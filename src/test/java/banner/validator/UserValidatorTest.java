package banner.validator;

import banner.model.Banner;
import banner.model.User;
import banner.model.enums.UserRoles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserValidatorTest {

    // указываем файл сообщений
    private static final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    static {
        messageSource.setBasename("messages");
    }

    private Validator userValidator;

    @Before
    public void setUp() throws Exception {
        userValidator = new UserValidator();
    }

    @Test
    public void validate() {
        User user = new User();
        user.setUsername("usernameusernameusernameusernameusernameusername");
        user.setPassword("12345678901234567890123456789012345678901234567890");
        user.setRole(UserRoles.ROLE_ADMIN);
        user.setActivity(true);

        final DataBinder dataBinder = new DataBinder(user);
        dataBinder.addValidators(userValidator);
        dataBinder.validate();

        Assert.assertTrue(dataBinder.getBindingResult().hasErrors());

        if (dataBinder.getBindingResult().hasErrors()) {
            dataBinder.getBindingResult().getAllErrors().stream().
                    forEach(e -> System.out.println(messageSource
                            .getMessage(e, Locale.getDefault())));
        }
    }
}