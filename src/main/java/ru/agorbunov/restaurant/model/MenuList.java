package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * Class represents menu list
 */
@Entity
@Table(name = "menu_lists")
public class MenuList extends BaseEntity {

    /*List of dishes that were include in menuList*/
    @OneToMany(mappedBy = "menuList")
    private List<Dish> dishList;

    /*
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

     */

    /*Date and Time when menuList was made*/
    @NotBlank
    @Column(name = "date_time", nullable = false)
    private LocalDate localDate;


    public MenuList() {
    }

    public MenuList(LocalDate date) {
        this.localDate = date;
    }


    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    /*
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

     */

    @Override
    public String toString() {
        return "MenuList{" +
                ", dateTime=" + localDate +
                '}';
    }
}
