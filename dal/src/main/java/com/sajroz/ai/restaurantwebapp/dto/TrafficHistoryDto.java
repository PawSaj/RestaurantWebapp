package com.sajroz.ai.restaurantwebapp.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class TrafficHistoryDto implements Serializable {

    private Long id;

    private TablesDto table;

    private OffsetDateTime tableOccupiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TablesDto getTable() {
        return table;
    }

    public void setTable(TablesDto table) {
        this.table = table;
    }

    public OffsetDateTime getTableOccupiedDate() {
        return tableOccupiedDate;
    }

    public void setTableOccupiedDate(OffsetDateTime tableOccupiedDate) {
        this.tableOccupiedDate = tableOccupiedDate;
    }

    @Override
    public String toString() {
        return "TrafficHistoryDto{" +
                "id=" + id +
                ", table=" + table +
                ", tableOccupiedDate=" + tableOccupiedDate +
                '}';
    }
}
