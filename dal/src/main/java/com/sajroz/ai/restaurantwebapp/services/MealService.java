package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.MealRepository;
import com.sajroz.ai.restaurantwebapp.dto.IngredientDto;
import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.mapping.MealMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.Meal;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MealService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MealRepository mealRepository;

    @Autowired
    MealMapper mealMapper;

    public JSONObject getAllMealsForMenu() {
        logger.debug("getAllMealsForMenu");
        List<Meal> meals;
        meals = mealRepository.findAll();

        List<MealDto> mealDtos = new ArrayList<>();
        for (Meal m : meals) {
            mealDtos.add(mealMapper.mealToMealDto(m));
        }
        logger.debug("getAllMealsForMenu, mealDtos={}", mealDtos);

        return generateJSONWithMenu(mealDtos);
    }

    private JSONObject generateJSONWithMenu(List<MealDto> mealDtos) {
        JSONObject mainObject = new JSONObject();
        for (MealDto m : mealDtos) {
            JSONObject jo = new JSONObject();
            jo.put("price", m.getPrice());
            jo.put("describe", m.getDescribe());
            JSONArray ingredients = new JSONArray();
            for (IngredientDto in:  m.getIngredients()) {
                ingredients.put(in.getName());
            }
            jo.put("ingredients", ingredients);
            jo.put("image", DatatypeConverter.printBase64Binary(m.getImage()));
            JSONArray ja = new JSONArray();
            ja.put(jo);
            mainObject.put(m.getName(), ja);
        }
        return mainObject;
    }
}
