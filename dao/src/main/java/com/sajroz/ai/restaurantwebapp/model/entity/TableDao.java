package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "TABLES")
public class TableDao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private Long id;

    @Column(name = "seats", nullable = false)
    private short seats;

    @Column(name = "x")
    private short x;

    @Column(name = "y")
    private short y;

    @Column(name = "floor")
    private short floor;

    @Column(name = "is_free", nullable = false)
    private boolean isFree;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="table")
    private Set<TableReservationDao> tableReservations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="table")
    private Set<ReservationHistoryDao> tableReservationsHistory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="table")
    private Set<TrafficHistoryDao> tableOccupations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public short getSeats() {
        return seats;
    }

    public void setSeats(short seats) {
        this.seats = seats;
    }

    public short getX() {
        return x;
    }

    public void setX(short x) {
        this.x = x;
    }

    public short getY() {
        return y;
    }

    public void setY(short y) {
        this.y = y;
    }

    public short getFloor() {
        return floor;
    }

    public void setFloor(short floor) {
        this.floor = floor;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public Set<TableReservationDao> getTableReservations() {
        return tableReservations;
    }

    public void setTableReservations(Set<TableReservationDao> tableReservations) {
        this.tableReservations = tableReservations;
    }

    public Set<ReservationHistoryDao> getTableReservationsHistory() {
        return tableReservationsHistory;
    }

    public void setTableReservationsHistory(Set<ReservationHistoryDao> tableReservationsHistory) {
        this.tableReservationsHistory = tableReservationsHistory;
    }

    public Set<TrafficHistoryDao> getTableOccupations() {
        return tableOccupations;
    }

    public void setTableOccupations(Set<TrafficHistoryDao> tableOccupations) {
        this.tableOccupations = tableOccupations;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", seats=" + seats +
                ", x=" + x +
                ", y=" + y +
                ", floor=" + floor +
                ", isFree=" + isFree +
                '}';
    }
}