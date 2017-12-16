package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.IngredientRepository;
import com.sajroz.ai.restaurantwebapp.dao.MealRepository;
import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.mapping.MealMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.Ingredient;
import com.sajroz.ai.restaurantwebapp.model.entity.Meal;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MealService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MealMapper mealMapper;

    @Autowired
    private JSONMessageGenerator jsonMessageGenerator;

    public JSONObject getAllMealsForMenu() {
        logger.info("getAllMealsForMenu");
        List<Meal> meals;
        meals = mealRepository.findAll();

        List<MealDto> mealDtos = new ArrayList<>();
        for (Meal m : meals) {
            mealDtos.add(mealMapper.mealToMealDto(m));
        }
        logger.info("getAllMealsForMenu, mealDtos={}", mealDtos);

        return jsonMessageGenerator.generateJSONWithMenu(mealDtos);
    }

    public String addMeal(MealDto mealDto) {
        Meal meal = mealMapper.mealDtoToMeal(mealDto);
        Set<Ingredient> correctIngredients = new HashSet<>();
        for (Ingredient i : meal.getIngredients()) {
            Ingredient mealIngredient = ingredientRepository.findByName(i.getName());
            if(mealIngredient == null) {
                mealIngredient = ingredientRepository.save(i);
            }
            correctIngredients.add(mealIngredient);
        }
        meal.getIngredients().clear();
        meal.getIngredients().addAll(correctIngredients);
        logger.info("addMeal meal with corrected ingredients information, meal={}", meal);
        mealRepository.save(meal);
        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
    }
}
