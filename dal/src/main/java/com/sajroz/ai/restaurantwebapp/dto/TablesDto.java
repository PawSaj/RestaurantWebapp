package com.sajroz.ai.restaurantwebapp.dto;

import com.sajroz.ai.restaurantwebapp.model.entity.ReservationHistory;
import com.sajroz.ai.restaurantwebapp.model.entity.TableReservation;
import com.sajroz.ai.restaurantwebapp.model.entity.TrafficHistory;

import java.util.Set;

public class TablesDto {

    private Long id;

    private short seats;

    private short x;

    private short y;

    private short floor;

    private boolean isFree;

    private Set<TableReservation> tableReservations;

    private Set<ReservationHistory> tableReservationsHistory;

    private Set<TrafficHistory> tableOccupations;

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

    public Set<TableReservation> getTableReservations() {
        return tableReservations;
    }

    public void setTableReservations(Set<TableReservation> tableReservations) {
        this.tableReservations = tableReservations;
    }

    public Set<ReservationHistory> getTableReservationsHistory() {
        return tableReservationsHistory;
    }

    public void setTableReservationsHistory(Set<ReservationHistory> tableReservationsHistory) {
        this.tableReservationsHistory = tableReservationsHistory;
    }

    public Set<TrafficHistory> getTableOccupations() {
        return tableOccupations;
    }

    public void setTableOccupations(Set<TrafficHistory> tableOccupations) {
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
