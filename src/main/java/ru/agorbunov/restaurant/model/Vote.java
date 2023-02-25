package ru.agorbunov.restaurant.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;



@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne(targetEntity = Restaurant.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(targetEntity = MenuList.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "MENU_LIST_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MenuList menuList;

    public Vote() {
    }

    public Vote(LocalDateTime dateTime, Restaurant restaurant, MenuList menuList) {
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.menuList = menuList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public MenuList getMenuList() {
        return menuList;
    }

    public void setMenuList(MenuList menuList) {
        this.menuList = menuList;
    }
}
