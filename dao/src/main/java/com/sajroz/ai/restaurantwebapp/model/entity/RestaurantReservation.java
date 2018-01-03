package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "RESTAURANT_RESERVATION")
public class RestaurantReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_reserving_user", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name = "restaurant_reservation_date", nullable = false)
    private LocalDate restaurantReservationDate;

    @Column(name = "reserved_floor", columnDefinition = "int default 0")
    private short floor;

    @Column(name = "reservation_describe")
    private String describe;

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
        return "RestaurantReservation{" +
                "id=" + id +
                ", user=" + user +
                ", restaurantReservationDate=" + restaurantReservationDate +
                ", floor=" + floor +
                ", describe=" + describe +
                '}';
    }
}
