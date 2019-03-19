package banner.validator;

import banner.model.Banner;
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
public class LocaleValidatorTest {

    // указываем файл сообщений
    private static final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    static {
        messageSource.setBasename("messages");
    }

    private Validator localeValidator;

    @Before
    public void setUp() throws Exception {
        localeValidator = new LocaleValidator();
    }

    @Test
    public void validate() {
        final banner.model.Locale locale = new banner.model.Locale();
        locale.setName("localeNamelocaleNamelocaleNamelocaleNamelocaleName");

        final DataBinder dataBinder = new DataBinder(locale);
        dataBinder.addValidators(localeValidator);
        dataBinder.validate();

        Assert.assertTrue(dataBinder.getBindingResult().hasErrors());

        if (dataBinder.getBindingResult().hasErrors()) {
            dataBinder.getBindingResult().getAllErrors().stream().
                    forEach(e -> System.out.println(messageSource
                            .getMessage(e, Locale.getDefault())));
        }
    }
}