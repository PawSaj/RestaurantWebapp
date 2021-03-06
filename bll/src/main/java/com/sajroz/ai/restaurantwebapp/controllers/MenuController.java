package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.services.MealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MealService mealService;

    @Autowired
    public MenuController(MealService mealService) {
        this.mealService = mealService;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = "application/json")
    public String sendMenu() {
        return mealService.getAllMealsForMenu();
    }


    @RequestMapping(value = "/admin/meal/{mealId}", method = RequestMethod.GET, produces = "application/json")
    public String sendMeal(@PathVariable Long mealId) {
        return mealService.getMeal(mealId);
    }

    @RequestMapping(value = "/admin/meal/categories", method = RequestMethod.GET, produces = "application/json")
    public String sendMealCategories() {
        return mealService.getMealCategories();
    }

    @RequestMapping(value = "/admin/meal", method = RequestMethod.POST, produces = "application/json")
    public String addMeal(@RequestBody MealDto mealDto) {
        logger.debug("addMeal Adding meal to database, meal={}", mealDto);
        return mealService.addMeal(mealDto);
    }

    @RequestMapping(value = "/admin/meal/{mealId}", method = RequestMethod.PUT, produces = "application/json")
    public String updateMeal(@PathVariable Long mealId, @RequestBody MealDto mealDto) {
        logger.debug("updateMeal Updating meal to database mealId={} to meal={}", mealId, mealDto);
        return mealService.updateMeal(mealId, mealDto);
    }

    @RequestMapping(value = "/admin/meal/{mealId}", method = RequestMethod.DELETE, produces = "application/json")
    public String deleteMeal(@PathVariable Long mealId) {
        logger.debug("deleteMeal Deleting meal from database, mealId={}", mealId);
        return mealService.deleteMeal(mealId);
    }
}
