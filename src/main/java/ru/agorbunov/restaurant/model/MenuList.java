package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Class represents menu list
 */
@NamedNativeQueries({
        @NamedNativeQuery(name = MenuList.BY_RESTAURANT, query = "SELECT * FROM menu_lists ml LEFT JOIN restaurant_menu rm on ml.id = rm.menu_list_id WHERE rm.restaurant_id=:id", resultClass = MenuList.class)
})
@Entity
@Table(name = "menu_lists")
public class MenuList extends BaseEntity {

    public static final String BY_RESTAURANT = "MenuList.byRestaurant";


    /*List of dishes that were include in menuList*/
    @OneToMany(mappedBy = "menuList")
    private Set<DishDescription> dishList;

    /*Date and Time when menuList was made*/
    @Column(name = "date", nullable = false)
    private LocalDate date;


    public MenuList() {
    }

    public MenuList(LocalDate date) {
        this.date = date;
    }


    public Set<DishDescription> getDishList() {
        return dishList;
    }

    public void setDishList(Set<DishDescription> dishList) {
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
