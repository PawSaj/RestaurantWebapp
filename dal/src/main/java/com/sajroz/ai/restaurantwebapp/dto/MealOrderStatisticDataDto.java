package com.sajroz.ai.restaurantwebapp.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealOrderStatisticDataDto {

    private LocalDate date;

    private Map<String, Long> mealOrder = new HashMap<>();

    public MealOrderStatisticDataDto(LocalDate date, List<MealDto> meals) {
        this.date = date;
        for (MealDto m : meals) {
            mealOrder.put(m.getName(), 0L);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Long> getMealOrder() {
        return mealOrder;
    }

    /*public void setMealOrder(Map<String, Long> mealOrder) {
        this.mealOrder = mealOrder;
    }*/

    @Override
    public String toString() {
        return "MealOrderStatisticDataDto{" +
                "date=" + date +
                ", mealOrder=" + mealOrder +
                '}';
    }

}
