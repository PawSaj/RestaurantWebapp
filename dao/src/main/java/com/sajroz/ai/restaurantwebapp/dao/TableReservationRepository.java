package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface TableReservationRepository extends JpaRepository<TableReservation, Long> {

    List<TableReservation> findAllByUserEmail(String email);

    @Query(value = "SELECT * " +
            "FROM table_reservation t " +
            "WHERE t.table_reservation_timestamp > DATE_SUB(?1, INTERVAL 2 HOUR) " +
            "AND t.table_reservation_timestamp < DATE_ADD(?1, INTERVAL 2 HOUR) " +
            "AND t.reserved_table = ?2 " +
            "ORDER BY t.table_reservation_timestamp ASC "
            , nativeQuery=true)
    List<TableReservation> checkTableIsFree(OffsetDateTime date, Long tableId);

    @Query(value = "SELECT * " +
            "FROM table_reservation t " +
            "WHERE t.table_reservation_timestamp >= ?1 " +
            "AND t.table_reservation_timestamp < DATE_ADD(?1, INTERVAL 1 WEEK) " +
            "ORDER BY t.table_reservation_timestamp ASC "
            , nativeQuery=true)
    List<TableReservation> getReservedTablesInWeek(OffsetDateTime startDate);

    @Query(value = "SELECT * " +
            "FROM table_reservation t " +
            "WHERE t.table_reservation_timestamp >= ?1 " +
            "AND t.table_reservation_timestamp < DATE_ADD(?1, INTERVAL 1 MONTH) " +
            "ORDER BY t.table_reservation_timestamp ASC "
            , nativeQuery=true)
    List<TableReservation> getReservedTablesInMonth(LocalDate startMonthDate);

    @Query(value = "SELECT IF(COUNT (*) > 0, 'false', 'true') " +
            "FROM table_reservation t " +
            "WHERE t.table_reservation_timestamp >= ?1 " +
            "AND t.table_reservation_timestamp < DATE_ADD(?1, INTERVAL 1 DAY) "
            , nativeQuery=true)
    boolean checkIsNoTableReservationOnDay(LocalDate restaurantReservationDate);
}
