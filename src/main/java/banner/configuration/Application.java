package banner.configuration;

import banner.service.UserDetailsServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "banner")
public class Application extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        String[] beanNames = ctx.getBeanDefinitionNames();

        Arrays.sort(beanNames);

        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(512));
        factory.setMaxRequestSize(DataSize.ofKilobytes(512));
        return factory.createMultipartConfig();
    }
}