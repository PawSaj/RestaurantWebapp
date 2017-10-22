package com.sajroz.ai.restaurantwebapp.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.sajroz.ai.restaurantwebapp"})
@ComponentScan(basePackages = {"com.sajroz.ai.restaurantwebapp"})
public class DaoConfig {

}
