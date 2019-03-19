package banner.validator;

import banner.model.Banner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.Locale;

@RunWith(SpringRunner.class)
public class BannerValidatorTest {
    // указываем файл сообщений
    private static final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    static {
        messageSource.setBasename("messages");
    }

    private Validator bannerValidator;

    @Before
    public void setUp() throws Exception {
        bannerValidator = new BannerValidator();
    }

    @Test
    public void validate() {
        final Banner banner = new Banner();
        banner.setTargetUrl("www.ya.ru");//This haven't http://
        banner.setHeight(600);
        banner.setWidth(1100);
        banner.setLangId(1);
        banner.setActivity(true);
        byte[] data = { (byte) 10 };

        final DataBinder dataBinder = new DataBinder(banner);
        dataBinder.addValidators(bannerValidator);
        dataBinder.validate();

        Assert.assertTrue(dataBinder.getBindingResult().hasErrors());

        if (dataBinder.getBindingResult().hasErrors()) {
            dataBinder.getBindingResult().getAllErrors().stream().
                    forEach(e -> System.out.println(messageSource
                            .getMessage(e, Locale.getDefault())));
        }
    }
}