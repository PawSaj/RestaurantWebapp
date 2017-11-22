package com.sajroz.ai.restaurantwebapp.model.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RESTAURANT_RESERVATION")
public class RestaurantReservationDao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_reservation_id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "restaurant_reserving_user", referencedColumnName = "user_id", nullable = false)
    private UserDao user;

    @Column(name = "restaurant_reservation_timestamp", nullable = false)
    @Type(type="timestamp") //added to be sure its timestamp
    private Date restaurantReservationDate;

    @Column(name = "reserved_floor")
    private short floor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDao getUser() {
        return user;
    }

    public void setUser(UserDao user) {
        this.user = user;
    }

    public Date getRestaurantReservationDate() {
        return restaurantReservationDate;
    }

    public void setRestaurantReservationDate(Date restaurantReservationDate) {
        this.restaurantReservationDate = restaurantReservationDate;
    }

    public short getFloor() {
        return floor;
    }

    public void setFloor(short floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "RestaurantReservation{" +
                "id=" + id +
                ", user=" + user +
                ", restaurantReservationDate=" + restaurantReservationDate +
                ", floor=" + floor +
                '}';
    }
}