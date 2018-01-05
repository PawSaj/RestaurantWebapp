package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "MEAL_ORDER_HISTORY")
public class MealOrderHistory implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_order_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordered_meal", referencedColumnName = "meal_id", nullable = false)
    private Meal meal;

    @Column(name = "meal_order_datetime", nullable = false)
    private OffsetDateTime mealOrderDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public OffsetDateTime getMealOrderDate() {
        return mealOrderDate;
    }

    public void setMealOrderDate(OffsetDateTime mealOrderDate) {
        this.mealOrderDate = mealOrderDate;
    }


}
