package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.RestaurantReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface RestaurantReservationRepository extends JpaRepository<RestaurantReservation, Long> {

    List<RestaurantReservation> findAllByUserEmail(String email);

    @Query(value = "SELECT * " +
            "FROM restaurant_reservation r " +
            "WHERE r.restaurant_reservation_date >= ?1 " +
            "AND r.restaurant_reservation_date < DATE_ADD(?1, INTERVAL 1 MONTH) " +
            "ORDER BY r.restaurant_reservation_date ASC "
            , nativeQuery=true)
    List<RestaurantReservation> getReservedRestaurantsInMonth(LocalDate startMonthDate);

    @Query(value = "SELECT * " +
            "FROM restaurant_reservation r " +
            "WHERE r.restaurant_reservation_date >= ?1 " +
            "AND r.restaurant_reservation_date < DATE_ADD(?1, INTERVAL 1 WEEK) " +
            "ORDER BY r.restaurant_reservation_date ASC "
            , nativeQuery=true)
    List<RestaurantReservation> getReservedRestaurantsInWeek(LocalDate startMonthDate);

    @Query(value = "SELECT IF(COUNT (*) > 0, 'false', 'true') " +
            "FROM restaurant_reservation r " +
            "WHERE r.restaurant_reservation_date = ?1 " +
            "ORDER BY r.restaurant_reservation_date ASC "
            , nativeQuery=true)
    boolean checkRestaurantIsFree(LocalDate date);

}
