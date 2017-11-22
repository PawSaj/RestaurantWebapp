package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MEAL")
public class Meal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Long id;

    @Column(name = "meal_name", nullable = false)
    private String name;

    @Column(name = "meal_price", columnDefinition = "Decimal(10,2)")
    private BigDecimal price;

    @Column(name = "meal_describe")
    private String describe;

    @Column(name = "meal_image")
    @Lob
    private byte[] image;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "MEAL_INGREDIENT",
            joinColumns = {@JoinColumn(name = "meal_id")},
            inverseJoinColumns = {@JoinColumn(name = "ingredient_id")})
    private Set<Ingredient> ingredients = new HashSet<Ingredient>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="meal")
    private Set<MealOrderHistory> mealOrderHistories;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<MealOrderHistory> getMealOrderHistories() {
        return mealOrderHistories;
    }

    public void setMealOrderHistories(Set<MealOrderHistory> mealOrderHistories) {
        this.mealOrderHistories = mealOrderHistories;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", describe='" + describe + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
