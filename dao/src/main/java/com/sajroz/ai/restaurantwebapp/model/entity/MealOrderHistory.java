package com.sajroz.ai.restaurantwebapp.model.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "MEAL_ORDER_HISTORY")
public class MealOrderHistory implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_order_history_id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ordered_meal", referencedColumnName = "meal_id", nullable = false)
    private MealDao meal;

    @Column(name = "meal_order_timestamp", nullable = false)
    @Type(type="timestamp") //added to be sure its timestamp
    private Date mealOrderDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MealDao getMeal() {
        return meal;
    }

    public void setMeal(MealDao meal) {
        this.meal = meal;
    }

    public Date getMealOrderDate() {
        return mealOrderDate;
    }

    public void setMealOrderDate(Date mealOrderDate) {
        this.mealOrderDate = mealOrderDate;
    }


}
