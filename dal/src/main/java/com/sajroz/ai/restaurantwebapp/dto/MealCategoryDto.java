package com.sajroz.ai.restaurantwebapp.dto;

import java.io.Serializable;

public class MealCategoryDto implements Serializable {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MealCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
