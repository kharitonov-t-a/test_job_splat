package banner.validator;

import banner.model.Banner;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

@Service
public class BannerValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Banner.class.isAssignableFrom(aClass) || ArrayList.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Banner banner = (Banner) obj;

        if(banner.getHeight()!=null){
            if(banner.getHeight() > 500 && banner.getHeight() < 1)
                errors.rejectValue("height", "value.oversize", "Height oversize 500");
        }else {
            errors.rejectValue("height", "value.notNull", "Field is empty");
        }

        if(banner.getWidth()!=null){
            if(banner.getWidth() > 1000 && banner.getWidth() < 1)
                errors.rejectValue("width", "value.oversize", "Width oversize 1000");
        }else {
            errors.rejectValue("width", "value.notNull", "Field is empty");
        }

        if(banner.getTargetUrl()!=null && banner.getTargetUrl().length() != 0){
            if(!isValidURL(banner.getTargetUrl()))
                errors.rejectValue("targetUrl", "value.invalidURL", "Invalid URL. Must start with http://");
        }else {
            errors.rejectValue("targetUrl", "value.notNull", "Field is empty");
        }




    }

    private boolean isValidURL(String url) {

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