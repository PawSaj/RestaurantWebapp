package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INGREDIENT")
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "ingredient_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private Set<Meal> meal = new HashSet<Meal>();

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

    public Set<Meal> getMeal() {
        return meal;
    }

    public void setMeal(Set<Meal> meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
