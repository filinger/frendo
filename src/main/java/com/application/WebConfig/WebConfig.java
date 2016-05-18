package com.application.WebConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
        super();
    }

    /**
     *  Dispatcher configuration for serving static resources
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        /*super.addResourceHandlers(registry);
        registry.addResourceHandler("/src/main/webapp/images/**").addResourceLocations("/src/main/webapp/images/");
        registry.addResourceHandler("/src/main/webapp/css/**").addResourceLocations("/src/main/webapp/css/");
        registry.addResourceHandler("/src/main/webapp/js/**").addResourceLocations("/src/main/webapp/js/");*/
    }

}
