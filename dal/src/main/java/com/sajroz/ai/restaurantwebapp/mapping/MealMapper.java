package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.MealDto;
import com.sajroz.ai.restaurantwebapp.model.entity.MealDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealMapper {
    //MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    MealDao mealDtoToMealDao(MealDto mealDto);
    MealDto mealDaoToMealDto(MealDao mealDao);
}
