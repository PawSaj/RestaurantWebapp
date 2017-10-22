package com.sajroz.ai.restaurantwebapp.services;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
@ComponentScan({"com.sajroz.ai.restaurantwebapp.services", "com.sajroz.ai.restaurantwebapp.mapping"})
public class ServiceConfig {

}
