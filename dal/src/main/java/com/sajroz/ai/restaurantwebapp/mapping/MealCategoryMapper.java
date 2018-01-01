package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.MealCategoryDto;
import com.sajroz.ai.restaurantwebapp.model.entity.MealCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealCategoryMapper {

    MealCategory mealCategoryDtoToMealCategory(MealCategoryDto mealCategoryDto);
    MealCategoryDto mealCategoryToMealCategoryDto(MealCategory mealCategory);
}
