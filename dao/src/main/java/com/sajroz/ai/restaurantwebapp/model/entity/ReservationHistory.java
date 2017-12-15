package com.sajroz.ai.restaurantwebapp.model.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RESERVATION_HISTORY")
public class ReservationHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserving_user_history", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_reserved_history", referencedColumnName = "table_id")
    private Tables table;

    @Column(name = "reserved_floor")
    private short floor;

    @Column(name = "table_reservation_timestamp_history", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tableReservationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Date getTableReservationDate() {
        return tableReservationDate;
    }

    public void setTableReservationDate(Date tableReservationDate) {
        this.tableReservationDate = tableReservationDate;
    }

    public short getFloor() {
        return floor;
    }

    public void setFloor(short floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "ReservationHistory{" +
                "id=" + id +
                ", user=" + user +
                ", table=" + table +
                ", floor=" + floor +
                ", tableReservationDate=" + tableReservationDate +
                '}';
    }
}
