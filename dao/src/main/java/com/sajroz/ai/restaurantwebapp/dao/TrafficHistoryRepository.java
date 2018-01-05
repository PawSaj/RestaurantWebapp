package com.sajroz.ai.restaurantwebapp.dao;

import com.sajroz.ai.restaurantwebapp.model.entity.TrafficHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface TrafficHistoryRepository extends JpaRepository<TrafficHistory, Long> {

    @Query(value = "SELECT * " +
            "FROM traffic_history t " +
            "WHERE t.table_occupied_datetime >= ?1 " +
            "AND t.table_occupied_datetime < ?2 " +
            "ORDER BY t.table_occupied_datetime ASC "
            , nativeQuery = true)
    List<TrafficHistory> getReservedTablesInRange(OffsetDateTime startDate, OffsetDateTime endDate);
}
