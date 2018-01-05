package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.MealOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface MealOrderHistoryRepository extends JpaRepository<MealOrderHistory, Long> {

    @Query(value = "SELECT * " +
            "FROM meal_order_history m " +
            "WHERE m.meal_order_datetime >= ?1 " +
            "AND m.meal_order_datetime < ?2 " +
            "ORDER BY m.meal_order_datetime ASC "
            , nativeQuery = true)
    List<MealOrderHistory> getReservedTablesInRange(OffsetDateTime startDate, OffsetDateTime endDate);

}
