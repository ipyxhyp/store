package ptr.store.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Web Configuration includes controllers path to scan and viewResolver bean definition
 *
 * */
@Configuration
@EnableWebMvc
@ComponentScan("ptr.store")
public class WebConfiguration implements WebMvcConfigurer {


    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/store/resources/**")
                .addResourceLocations("/public-resources/");
    }

    public void configureViewResolvers(ViewResolverRegistry registry) {

        registry.jsp("/WEB-INF/jsp/", ".jsp");
        registry.viewResolver(new BeanNameViewResolver());
        registry.enableContentNegotiation(new MappingJackson2JsonView());
    }

    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/management").setViewName("store");
        
    }


}
