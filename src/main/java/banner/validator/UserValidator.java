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

        if(user.getUsername()!=null && user.getUsername().length() != 0){
            if(user.getUsername().length()>45)
                errors.rejectValue("username", "value.exceed", "Username exceed 45 characters");
        }else {
            errors.rejectValue("username", "value.notNull", "Field is empty");
        }

        if(user.getPassword()!=null && user.getPassword().length() != 0){
            if(user.getPassword().length()>45)
                errors.rejectValue("password", "value.exceed", "Password exceed 45 characters");
        }else {
            errors.rejectValue("password", "value.notNull", "Field is empty");

        }
    }
}