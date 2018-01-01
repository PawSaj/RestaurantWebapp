package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dao.IngredientRepository;
import com.sajroz.ai.restaurantwebapp.dao.MealCategoryRepository;
import com.sajroz.ai.restaurantwebapp.dao.MealRepository;
import com.sajroz.ai.restaurantwebapp.dto.MealCategoryDto;
import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.mapping.MealCategoryMapper;
import com.sajroz.ai.restaurantwebapp.mapping.MealMapper;
import com.sajroz.ai.restaurantwebapp.model.entity.Ingredient;
import com.sajroz.ai.restaurantwebapp.model.entity.Meal;
import com.sajroz.ai.restaurantwebapp.model.entity.MealCategory;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
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

    private final MealCategoryRepository mealCategoryRepository;

    private final JSONMessageGenerator jsonMessageGenerator;

    private final MealCategoryMapper mealCategoryMapper;

    @Autowired
    public MealService(MealRepository mealRepository, IngredientRepository ingredientRepository, MealMapper mealMapper, JSONMessageGenerator jsonMessageGenerator, MealCategoryRepository mealCategoryRepository, MealCategoryMapper mealCategoryMapper) {
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
        this.mealMapper = mealMapper;
        this.jsonMessageGenerator = jsonMessageGenerator;
        this.mealCategoryRepository = mealCategoryRepository;
        this.mealCategoryMapper = mealCategoryMapper;
    }

    public String getAllMealsForMenu() {
        logger.debug("getAllMealsForMenu");
        List<Meal> meals;
        meals = mealRepository.findAllByOrderByMealCategory();

        List<MealDto> mealDtos = new ArrayList<>(meals.size());
        for (Meal m : meals) {
            mealDtos.add(mealMapper.mealToMealDto(m));
        }
        logger.debug("getAllMealsForMenu, mealDtos={}", mealDtos);

        return jsonMessageGenerator.generateJSONWithMenu(mealDtos).toString();
    }

    public String addMeal(MealDto mealDto) {
        Meal meal = mealMapper.mealDtoToMeal(mealDto);
        String verifyMealDataResponse = verifyMealData(meal);
        if (verifyMealDataResponse == null) {
            if (isMealExist(meal)) {
                logger.warn("addMeal Meal adding failed - meal already exist, meal={}", mealDto);
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.DUPLICATE_MEAL).toString();
            }
            meal.setId(null);
            saveMeal(meal);
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.OK).toString();
        } else {
            return verifyMealDataResponse;
        }
    }

    private String verifyMealData(Meal meal) {
        if (meal.getName() == null) {
            return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.MISSING_DATA, "missing", "name").toString();
        } else if (!checkCategoryExists(meal.getMealCategory().getName())) {
            //return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_MEAL_CATEGORY).toString();
            mealCategoryRepository.save(meal.getMealCategory());
        }
        return null;
    }

    private boolean checkCategoryExists(String mealCategory) {
        return mealCategoryRepository.findByName(mealCategory) != null;
    }

    private void saveMeal(Meal meal) {
        meal = correctIngredientsInMeal(meal);
        meal = correctMealCategory(meal);
        logger.debug("Saving meal with corrected ingredients information, meal={}", meal);
        mealRepository.save(meal);
    }

    private Meal correctMealCategory(Meal meal) {
        meal.setMealCategory(mealCategoryRepository.findByName(meal.getMealCategory().getName()));
        return meal;
    }

    private boolean isMealExist(Meal meal) {
        return mealRepository.findByName(meal.getName()) != null;

    }

    private Meal correctIngredientsInMeal(Meal meal) {
        Set<Ingredient> correctIngredients = new HashSet<>(meal.getIngredients().size());
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
        Meal meal = mealMapper.mealDtoToMeal(mealDto);
        String verifyMealDataResponse = verifyMealData(meal);
        if (verifyMealDataResponse == null) {
            if (!mealRepository.exists(mealId)) {
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_MEAL).toString();
            }
            if (isMealExist(meal) && !isSelfUpdate(mealId, meal)) {
                return jsonMessageGenerator.createSimpleResponse(ResponseMessages.DUPLICATE_MEAL).toString();
            }

            meal.setId(mealId);
            saveMeal(meal);
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.OK).toString();
        } else {
            return verifyMealDataResponse;
        }

    }

    private boolean isSelfUpdate(Long mealId, Meal meal) {
        return mealRepository.findOne(mealId).getName().equals(meal.getName());
    }

    public String deleteMeal(Long mealId) {
        if (!mealRepository.exists(mealId)) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_MEAL).toString();
        }
        Long mealCategoryId = mealRepository.findOne(mealId).getMealCategory().getId();
        mealRepository.delete(mealId);
        if (mealRepository.categoryIsEmpty(mealCategoryId)) {
            mealCategoryRepository.delete(mealCategoryId);
        }
        return jsonMessageGenerator.createSimpleResponse(ResponseMessages.OK).toString();
    }

    public String getMeal(Long mealId) {
        if (!mealRepository.exists(mealId)) {
            return jsonMessageGenerator.createSimpleResponse(ResponseMessages.NO_MEAL).toString();
        }
        MealDto mealDto = mealMapper.mealToMealDto(mealRepository.findOne(mealId));
        return jsonMessageGenerator.convertMealToJSON(mealDto).put("category", mealDto.getMealCategory().getName()).toString();
    }

    public String getMealCategories() {
        List<MealCategory> mealCategories = mealCategoryRepository.findAll();
        List<MealCategoryDto> mealCategoryDto = new ArrayList<>(mealCategories.size());
        for(MealCategory mc : mealCategories) {
            mealCategoryDto.add(mealCategoryMapper.mealCategoryToMealCategoryDto(mc));
        }
        return jsonMessageGenerator.generateJSONWithMealsCategories(mealCategoryDto).toString();
    }
}
