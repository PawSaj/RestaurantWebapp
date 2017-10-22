package com.sajroz.ai.restaurantwebapp.config;

import com.sajroz.ai.restaurantwebapp.security.SecurityJavaConfig;
import com.sajroz.ai.restaurantwebapp.services.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({"com.sajroz.ai.restaurantwebapp.security",
        "com.sajroz.ai.restaurantwebapp.controllers",
        "com.sajroz.ai.restaurantwebapp.services"})
@Import({ServiceConfig.class, SecurityJavaConfig.class, SpringConfigDal.class})
public class SpringConfig extends WebMvcConfigurerAdapter {


}
