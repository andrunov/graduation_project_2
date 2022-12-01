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
    @OneToMany(mappedBy = "menu_lists", fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_list_id")
    private List<Dish> dishList;


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


    @Override
    public String toString() {
        return "MenuList{" +
                ", dateTime=" + localDate +
                '}';
    }
}
