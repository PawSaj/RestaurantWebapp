package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.services.MealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MealService mealService;

    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String sendMenu(){
        return mealService.getAllMealsForMenu().toString();
    }



}
