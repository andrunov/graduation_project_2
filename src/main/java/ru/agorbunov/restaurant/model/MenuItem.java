package ru.agorbunov.restaurant.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "menu_items")
public class MenuItem extends BaseEntity{


    /*price of dish*/
    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(targetEntity = MenuList.class)
    @JoinColumn(name = "menu_list_id", referencedColumnName = "id")
    @JsonIgnore
    private MenuList menuList;

    @OneToOne(targetEntity = Dish.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dish dish;

    public MenuItem() {
    }

    public MenuItem(MenuList menuList, Dish dish, Double price) {
        this.price = price;
        this.menuList = menuList;
        this.dish = dish;
    }

    public MenuItem(int id, MenuList menuList, Dish dish, Double price) {
        this.id = id;
        this.price = price;
        this.menuList = menuList;
        this.dish = dish;
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

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
