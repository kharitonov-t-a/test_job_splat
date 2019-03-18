package banner.validator;

import banner.model.Banner;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class ImageValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        MultipartFile multipartFile = (MultipartFile) obj;

        if(multipartFile.getSize() > 500000)
            errors.rejectValue("image", "value.heavier");
    }
}