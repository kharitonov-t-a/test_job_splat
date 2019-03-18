package banner.validator;

import banner.model.Banner;
import banner.model.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class BannerValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Banner.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Banner user = (Banner) obj;
        if(user.getHeight() > 500)
            errors.rejectValue("height", "value.oversize");
        if(user.getWidth() > 1000)
            errors.rejectValue("width", "value.oversize");
        if(!isValidURL(user.getTargetUrl()))
            errors.rejectValue("targetUrl", "value.invalidURL");
    }


    public boolean isValidURL(String url) {

        URL u = null;

        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }

        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return false;
        }

        return true;
    }
}