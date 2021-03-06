package com.sajroz.ai.restaurantwebapp.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "TABLE_RESERVATION")
public class TableReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_reserving_user", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserved_table", referencedColumnName = "table_id", nullable = false)
    private Tables table;

    @Column(name = "table_reservation_timestamp", nullable = false)
    private OffsetDateTime tableReservationDate;

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

    public OffsetDateTime getTableReservationDate() {
        return tableReservationDate;
    }

    public void setTableReservationDate(OffsetDateTime tableReservationDate) {
        this.tableReservationDate = tableReservationDate;
    }

    @Override
    public String toString() {
        return "TableReservation{" +
                "id=" + id +
                ", user=" + user +
                ", table=" + table +
                ", tableReservationDate=" + tableReservationDate +
                '}';
    }
}
