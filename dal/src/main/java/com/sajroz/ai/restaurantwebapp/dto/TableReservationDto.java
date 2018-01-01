package com.sajroz.ai.restaurantwebapp.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class TableReservationDto implements Serializable {

    private Long id;

    private UserDto user;

    private TablesDto table;

    private OffsetDateTime tableReservationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public TablesDto getTable() {
        return table;
    }

    public void setTable(TablesDto table) {
        this.table = table;
    }

    public OffsetDateTime getTableReservationDate() {
        return tableReservationDate;
    }

    public void setTableReservationDate(OffsetDateTime tableReservationDate) {
        this.tableReservationDate = tableReservationDate;
    }

    @Override
    public String toString() {
        return "TableReservation{" +
                "id=" + id +
                ", user=" + user +
                ", table=" + table +
                ", tableReservationDate=" + tableReservationDate +
                '}';
    }
}
