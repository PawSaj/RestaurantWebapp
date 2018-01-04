package com.sajroz.ai.restaurantwebapp.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class MealOrderHistoryDto implements Serializable {

    private Long id;

    private MealDto meal;

    private OffsetDateTime mealOrderDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MealDto getMeal() {
        return meal;
    }

    public void setMeal(MealDto meal) {
        this.meal = meal;
    }

    public OffsetDateTime getMealOrderDate() {
        return mealOrderDate;
    }

    public void setMealOrderDate(OffsetDateTime mealOrderDate) {
        this.mealOrderDate = mealOrderDate;
    }

    @Override
    public String toString() {
        return "MealOrderHistoryDto{" +
                "id=" + id +
                ", meal=" + meal +
                ", mealOrderDate=" + mealOrderDate +
                '}';
    }
}
