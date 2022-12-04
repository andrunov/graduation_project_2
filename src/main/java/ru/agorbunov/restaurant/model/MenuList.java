package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Class represents menu list
 */
@Entity
@Table(name = "menu_lists")
public class MenuList extends BaseEntity {

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
