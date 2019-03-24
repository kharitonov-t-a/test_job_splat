package banner;

import banner.service.UserDetailsServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class ApplicationInitializer extends SpringBootServletInitializer
{

//    @Value("${server.servlet.context-path}")
//    private static String contextPath;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationInitializer.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(ApplicationInitializer.class, args);
    }

//    public static void main(String[] args)
//    {
//        ApplicationContext ctx = SpringApplication.run(Application.class, args);
//
//        String[] beanNames = ctx.getBeanDefinitionNames();
//
//        Arrays.sort(beanNames);
//
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
//    }
//
@Bean
public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
}
    @Bean
    public ErrorPageRegistrar errorPageRegistrar(){
        return new MyErrorPageRegistrar();
    }

    private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
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

//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
//    webServerFactoryCustomizer() {
//        return factory -> factory.setContextPath("/baeldung");
//    }

//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("messages");
//        return messageSource;
//    }
}