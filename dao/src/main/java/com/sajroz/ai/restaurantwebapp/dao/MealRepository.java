package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findAllByOrderByMealCategory();

    Meal findByName(String mealName);

    @Query(value = "SELECT CASE WHEN count(*) > 0 THEN 'false' ELSE 'true' END FROM Meal m WHERE m.category = ?1", nativeQuery = true)
    boolean categoryIsEmpty(Long categoryId);
}
