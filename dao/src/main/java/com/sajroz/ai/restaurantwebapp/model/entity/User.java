package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "user_role", nullable = false)
    private String role;

    @Column(name = "user_image")
    private String image;

    @OneToMany(mappedBy="user")
    private Set<TableReservation> tableReservationSet;

    @OneToMany(mappedBy="user")
    private Set<RestaurantReservation> restaurantReservationSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<TableReservation> getTableReservationSet() {
        return tableReservationSet;
    }

    public void setTableReservationSet(Set<TableReservation> tableReservationSet) {
        this.tableReservationSet = tableReservationSet;
    }

    public Set<RestaurantReservation> getRestaurantReservationSet() {
        return restaurantReservationSet;
    }

    public void setRestaurantReservationSet(Set<RestaurantReservation> restaurantReservationSet) {
        this.restaurantReservationSet = restaurantReservationSet;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
