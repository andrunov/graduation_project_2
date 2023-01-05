package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import java.time.LocalDateTime;



@NamedQueries({
        @NamedQuery(name = Vote.BY_USER, query = "select v from Vote v where v.user.id=:id"),
        @NamedQuery(name = Vote.BY_RESTAURANT_DATE, query = "select v from Vote v where v.restaurant.id = :id and v.dateTime >= :from and v.dateTime < :to"),
        @NamedQuery(name = Vote.BY_USER_DATE, query = "select v from Vote v where v.user.id = :id and v.dateTime >= :from and v.dateTime < :to"),
        @NamedQuery(name = Vote.BY_USER_RESTAURANT, query = "select v from Vote v where v.user.id = :user_id and v.restaurant.id = :restaurant_id ")
})
@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

    public static final String BY_USER = "Vote.getByUser";
    public static final String BY_RESTAURANT_DATE = "Vote.getByRestaurantAndDate";
    public static final String BY_USER_DATE = "Vote.getByUserAndDate";
    public static final String BY_USER_RESTAURANT = "Vote.getByUserAndRestaurant";

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
