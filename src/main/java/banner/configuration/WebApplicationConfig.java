//package banner.configuration;
//
//import org.springframework.boot.web.server.ErrorPage;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Configuration
//public class WebApplicationConfig extends WebMvcConfigurerAdapter implements HandlerExceptionResolver {
//
////    @Override
////    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addViewController("/notFound").setViewName("forward:/index.html");
////    }
////
////
////    @Bean
////    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
////        return container -> {
////            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,
////                    "/notFound"));
////        };
////    }
//
//}