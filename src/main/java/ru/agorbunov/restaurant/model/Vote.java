package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import java.time.LocalDateTime;


@NamedNativeQueries({
        @NamedNativeQuery(name = Vote.BY_USER, query = "SELECT * FROM votes v LEFT JOIN user_votes uv on v.restaurant_id = uv.restaurant_id where uv.user_id=:id", resultClass = Vote.class)
})
@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

    public static final String BY_USER = "Vote.getByUser";

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne(targetEntity = Restaurant.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(LocalDateTime dateTime, Restaurant restaurant) {
        this.dateTime = dateTime;
        this.restaurant = restaurant;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
