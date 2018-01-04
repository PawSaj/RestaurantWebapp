package com.sajroz.ai.restaurantwebapp.mapping;

import com.sajroz.ai.restaurantwebapp.dto.MealOrderHistoryDto;
import com.sajroz.ai.restaurantwebapp.model.entity.MealOrderHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealOrderHistoryMapper {

    MealOrderHistory mealOrderHistoryDtoToMealOrderHistory(MealOrderHistoryDto mealOrderHistoryDto);
    MealOrderHistoryDto mealOrderHistoryToMealOrderHistoryDto(MealOrderHistory mealOrderHistory);
}
