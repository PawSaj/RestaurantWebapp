package com.sajroz.ai.restaurantwebapp.model.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name = "restaurant_reservation_timestamp", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date restaurantReservationDate;

    @Column(name = "reserved_floor")
    private short floor;

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
