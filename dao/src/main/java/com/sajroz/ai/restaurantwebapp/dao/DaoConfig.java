package com.sajroz.ai.restaurantwebapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@Configuration
@EnableJpaRepositories(basePackages = {"com.sajroz.ai.restaurantwebapp"})
@ComponentScan(basePackages = {"com.sajroz.ai.restaurantwebapp"})
public class DaoConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void init() {
        logger.info("INITIALIZE DaoConfig");
    }
}
