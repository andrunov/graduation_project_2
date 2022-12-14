package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Class represents menu list
 */

@NamedQueries({
        @NamedQuery(name = MenuList.BY_RESTAURANT, query = "select ml from MenuList ml where ml.restaurant.id=:id"),
        @NamedQuery(name = MenuList.BY_RESTAURANT_AND_DATE, query = "select ml from MenuList ml where ml.restaurant.id=:id and ml.date=:date")
})
@Entity
@Table(name = "menu_lists")
public class MenuList extends BaseEntity {

    public static final String BY_RESTAURANT = "MenuList.byRestaurant";
    public static final String BY_RESTAURANT_AND_DATE = "MenuList.byRestaurantAndDate";

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    /*List of dishes that were include in menuList*/
    @OneToMany(mappedBy = "menuList")
    private List<MenuItem> items;

    /*Date and Time when menuList was made*/
    @Column(name = "date", nullable = false)
    private LocalDate date;


    public MenuList() {
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> dishList) {
        this.items = dishList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate localDate) {
        this.date = localDate;
    }


    @Override
    public String toString() {
        return "MenuList{" +
                ", dateTime=" + date +
                '}';
    }
}
