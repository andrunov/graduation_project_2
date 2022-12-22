package ru.agorbunov.restaurant.to;

import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public class MenuListTo {

    public static MenuListTo fromMenuList(MenuList source) {
        MenuListTo result = new MenuListTo();
        result.id = source.getId();
        result.restaurant = source.getRestaurant();
        result.date = source.getDate();
        return result;
    }

    public static MenuList toMenuList(MenuListTo source) {
        MenuList result = new MenuList();
        result.setId(source.getId());
        result.setRestaurant(source.getRestaurant());
        result.setDate(source.getDate());
        return result;
    }

    private Integer id;
    private Restaurant restaurant;
    private LocalDate date;
    private List<MenuItemTo> items;

    public MenuListTo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<MenuItemTo> getItems() {
        return items;
    }

    public void setItems(List<MenuItemTo> items) {
        this.items = items;
    }
}
