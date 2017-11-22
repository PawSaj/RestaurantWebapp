package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.model.entity.Meal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealMapper {
    //MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    Meal mealDtoToMeal(MealDto mealDto);
    MealDto mealToMealDto(Meal meal);
}
