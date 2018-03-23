package ptr.store.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class WebConfigInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
//                InMemoryConfiguration.class
        };
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfiguration.class };
    }

    protected String[] getServletMappings() {
        return new String[] { "/", "/management"} ;
    }
}
