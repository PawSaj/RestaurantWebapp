package com.sajroz.ai.restaurantwebapp.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class MealDto {

    private String name;

    private BigDecimal price;

    private String describe;

    private byte[] image;

    private Set<IngredientDto> ingredients = new HashSet<>();

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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
        return "Meal{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", describe='" + describe + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
