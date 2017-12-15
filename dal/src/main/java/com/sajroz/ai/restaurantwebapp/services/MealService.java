package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.MealRepository;
import com.sajroz.ai.restaurantwebapp.dto.IngredientDto;
import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.mapping.MealMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.Meal;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MealService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MealMapper mealMapper;

    @Autowired
    private JSONMessageGenerator jsonMessageGenerator;

    public JSONObject getAllMealsForMenu() {
        logger.debug("getAllMealsForMenu");
        List<Meal> meals;
        meals = mealRepository.findAll();

        List<MealDto> mealDtos = new ArrayList<>();
        for (Meal m : meals) {
            mealDtos.add(mealMapper.mealToMealDto(m));
        }
        logger.debug("getAllMealsForMenu, mealDtos={}", mealDtos);

        return jsonMessageGenerator.generateJSONWithMenu(mealDtos);
    }

}
