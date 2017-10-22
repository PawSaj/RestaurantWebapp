package com.sajroz.ai.restaurantwebapp.config;

import com.sajroz.ai.restaurantwebapp.dao.DaoConfig;
import com.sajroz.ai.restaurantwebapp.model.ModelConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"com.sajroz.ai.restaurantwebapp.dao"})
@Import({ModelConfig.class, DaoConfig.class})
public class SpringConfigDal {


}
