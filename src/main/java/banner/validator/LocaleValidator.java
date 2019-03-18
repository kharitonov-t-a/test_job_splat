package banner.validator;

import banner.model.Locale;
import banner.model.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class LocaleValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Locale.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Locale locale = (Locale) obj;
        if(locale.getName().length()>45)
            errors.rejectValue("locale", "value.exceed");
    }
}