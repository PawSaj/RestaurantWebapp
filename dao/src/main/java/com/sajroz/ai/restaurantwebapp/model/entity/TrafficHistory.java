package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "TRAFFIC_HISTORY")
public class TrafficHistory implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "traffic_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupied_table", referencedColumnName = "table_id", nullable = false)
    private Tables table;

    @Column(name = "table_occupied_datetime", nullable = false)
    private OffsetDateTime tableOccupiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
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
        return "TrafficHistory{" +
                "id=" + id +
                ", table=" + table +
                ", tableOccupiedDate=" + tableOccupiedDate +
                '}';
    }
}
