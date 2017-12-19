package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import com.sajroz.ai.restaurantwebapp.services.MealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MealService mealService;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public MenuController(MealService mealService, JSONMessageGenerator jsonMessageGenerator) {
        this.mealService = mealService;
        this.jsonMessageGenerator = jsonMessageGenerator;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String sendMenu(){
        return mealService.getAllMealsForMenu().toString();
    }


    @RequestMapping(value = "/admin/addMeal", method = RequestMethod.POST, produces = "application/json")
    public String addMeal(@RequestBody MealDto mealDto) {
        try {
            logger.info("Maving meal to database, meal={}", mealDto);
            return mealService.addMeal(mealDto);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Meal adding failed - meal already exist, meal={}", mealDto);
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.DUPLICATE_MEAL).toString();
        }

    }
}
