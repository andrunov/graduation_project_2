package ru.agorbunov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;



@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "vote_unique_user_datetime_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne(targetEntity = Restaurant.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(targetEntity = MenuList.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "MENU_LIST_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MenuList menuList;

    public Vote(LocalDateTime dateTime, Restaurant restaurant, MenuList menuList) {
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.menuList = menuList;
    }

    public Vote(int id, LocalDateTime dateTime, Restaurant restaurant, MenuList menuList) {
        this.id = id;
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.menuList = menuList;
    }

}
