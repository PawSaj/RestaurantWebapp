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

    private final MealRepository mealRepository;

    private final IngredientRepository ingredientRepository;

    private final MealMapper mealMapper;

    private final JSONMessageGenerator jsonMessageGenerator;

    @Autowired
    public MealService(MealRepository mealRepository, IngredientRepository ingredientRepository, MealMapper mealMapper, JSONMessageGenerator jsonMessageGenerator) {
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
        this.mealMapper = mealMapper;
        this.jsonMessageGenerator = jsonMessageGenerator;
    }

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
        if (isMealExist(mealDto)) {
            logger.warn("addMeal Meal adding failed - meal already exist, meal={}", mealDto);
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.DUPLICATE_MEAL).toString();
        }
        mealDto.setId(null);
        saveMeal(mealDto);
        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
    }

    private void saveMeal(MealDto mealDto) {
        Meal meal = mealMapper.mealDtoToMeal(mealDto);
        meal = correctIngredientsInMeal(meal);

        logger.debug("Saving meal with corrected ingredients information, meal={}", meal);
        mealRepository.save(meal);
    }

    private boolean isMealExist(MealDto mealDto) {
        return mealRepository.findByName(mealDto.getName()) != null;

    }

    private Meal correctIngredientsInMeal(Meal meal) {
        Set<Ingredient> correctIngredients = new HashSet<>();
        for (Ingredient i : meal.getIngredients()) {
            Ingredient mealIngredient = ingredientRepository.findByName(i.getName());
            if (mealIngredient == null) {
                mealIngredient = ingredientRepository.save(i);
            }
            correctIngredients.add(mealIngredient);
        }
        meal.getIngredients().clear();
        meal.getIngredients().addAll(correctIngredients);
        return meal;
    }

    public String updateMeal(Long mealId, MealDto mealDto) {
        if (!mealRepository.exists(mealId)) {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_MEAL).toString();
        }
        if (isMealExist(mealDto) && !isSelfUpdate(mealId, mealDto)) {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.DUPLICATE_MEAL).toString();
        }

        mealDto.setId(mealId);
        saveMeal(mealDto);
        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
    }

    private boolean isSelfUpdate(Long mealId, MealDto mealDto) {
        return mealRepository.findOne(mealId).getName().equals(mealDto.getName());
    }

    public String deleteMeal(Long mealId) {
        if (!mealRepository.exists(mealId)) {
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_MEAL).toString();
        }
        
        mealRepository.delete(mealId);
        return jsonMessageGenerator.createSimpleRespons(ResponseMessages.OK).toString();
    }
}
