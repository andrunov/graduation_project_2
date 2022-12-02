package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class represents dish
 */
@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {

    /*Name of dish*/
    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    private String name;

    /*price of dish*/
    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(targetEntity = MenuList.class)
    @JoinColumn(name = "menu_list_id", referencedColumnName = "id")
    private MenuList menuList;

    public Dish() {
    }

    /** Constructor
     */
    public Dish(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public MenuList getMenuList() {
        return menuList;
    }

    public void setMenuList(MenuList menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", description='" + name + '\'' +
                '}';
    }
}
