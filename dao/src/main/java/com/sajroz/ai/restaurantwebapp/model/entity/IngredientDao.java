package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INGREDIENT")
public class IngredientDao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "ingredient_name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.EAGER)
    private Set<MealDao> meal = new HashSet<MealDao>();

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

    public Set<MealDao> getMeal() {
        return meal;
    }

    public void setMeal(Set<MealDao> meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", meal=" + meal +
                '}';
    }
}
