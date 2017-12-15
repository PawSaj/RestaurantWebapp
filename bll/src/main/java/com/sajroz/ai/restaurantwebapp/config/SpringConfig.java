package com.sajroz.ai.restaurantwebapp.config;

import com.sajroz.ai.restaurantwebapp.security.SecurityJavaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebMvc
@ComponentScan({"com.sajroz.ai.restaurantwebapp.security",
        "com.sajroz.ai.restaurantwebapp.controllers"})
@Import({SecurityJavaConfig.class, SpringConfigDal.class})
public class SpringConfig extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void init() {
        logger.info("INITIALIZE SpringConfig");
    }

    //to ignore dots in URL's as extension of files
    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(false);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
}
