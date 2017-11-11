package com.sajroz.ai.restaurantwebapp.model.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TRAFFIC_HISTORY")
public class TrafficHistoryDao implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "traffic_history_id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "occupied_table", referencedColumnName = "table_id", nullable = false)
    private TableDao table;

    @Column(name = "table_occupied_timestamp", nullable = false)
    @Type(type="timestamp") //added to be sure its timestamp
    private Date tableOccupiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TableDao getTable() {
        return table;
    }

    public void setTable(TableDao table) {
        this.table = table;
    }

    public Date getTableOccupiedDate() {
        return tableOccupiedDate;
    }

    public void setTableOccupiedDate(Date tableOccupiedDate) {
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
