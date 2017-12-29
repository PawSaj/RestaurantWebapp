package com.sajroz.ai.restaurantwebapp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class MealDto implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private String describe;

    private String image;

    private Set<IngredientDto> ingredients = new HashSet<>();

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "MealDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", describe='" + describe + '\'' +
                ", image='" + image + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
