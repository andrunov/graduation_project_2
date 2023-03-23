package ru.agorbunov.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

/**
 * Class represents menu list
 */

@Entity
@Table(name = "menu_lists")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MenuList extends BaseEntity {

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @JsonIgnore
    private Restaurant restaurant;

    /*List of dishes that were include in menuList*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menuList")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MenuItem> items;

    /*Date and Time when menuList was made*/
    @Column(name = "date", nullable = false)
    private LocalDate date;


    public MenuList(int id, LocalDate date) {
        this.id = id;
        this.date = date;
    }


    @Override
    public String toString() {
        return "MenuList{" +
                ", dateTime=" + date +
                '}';
    }
}
