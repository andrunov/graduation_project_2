package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Class represents menu list
 */

@NamedQueries({
        @NamedQuery(name = MenuList.BY_RESTAURANT, query = "select ml from MenuList ml where ml.restaurant.id=:id")
})
@Entity
@Table(name = "menu_lists")
public class MenuList extends BaseEntity {

    public static final String BY_RESTAURANT = "MenuList.byRestaurant";

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    /*List of dishes that were include in menuList*/
    @OneToMany(mappedBy = "menuList")
    private List<DishDescription> dishList;

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

    public List<DishDescription> getDishList() {
        return dishList;
    }

    public void setDishList(List<DishDescription> dishList) {
        this.dishList = dishList;
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
