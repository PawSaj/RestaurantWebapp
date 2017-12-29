package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.TableReservation;
import com.sajroz.ai.restaurantwebapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface TableReservationRepository extends JpaRepository<TableReservation, Long> {

    List<TableReservation> findAllByUser(User user);

    @Query(value = "SELECT *" +
            "FROM table_reservation t " +
            "WHERE t.table_reservation_timestamp >= ?1 " +
            "AND t.table_reservation_timestamp < DATE_ADD(?1, INTERVAL 2 HOUR) " +
            "AND t.reserved_table = ?2 " +
            "LIMIT 1", nativeQuery=true)
    TableReservation checkTableIsFree(OffsetDateTime date, Long tableId);
}
