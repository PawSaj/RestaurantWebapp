package com.sajroz.ai.restaurantwebapp.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class RestaurantReservationDto implements Serializable{

    private Long id;

    private UserDto user;

    private LocalDate restaurantReservationDate;

    private short floor;

    private String describe;

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

    public LocalDate getRestaurantReservationDate() {
        return restaurantReservationDate;
    }

    public void setRestaurantReservationDate(LocalDate restaurantReservationDate) {
        this.restaurantReservationDate = restaurantReservationDate;
    }

    public short getFloor() {
        return floor;
    }

    public void setFloor(short floor) {
        this.floor = floor;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "RestaurantReservationDto{" +
                "id=" + id +
                ", user=" + user +
                ", restaurantReservationDate=" + restaurantReservationDate +
                ", floor=" + floor +
                ", describe='" + describe + '\'' +
                '}';
    }
}
