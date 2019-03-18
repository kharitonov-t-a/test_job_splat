package banner.validator;

import banner.model.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        if(user.getUsername().length()>45)
            errors.rejectValue("username", "value.exceed");
        if(user.getPassword().length()>120)
            errors.rejectValue("password", "value.exceed");
    }
}