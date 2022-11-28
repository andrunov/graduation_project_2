package ru.agorbunov.restaurant.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class represents restaurant
 */
public class Restaurant extends BaseEntity {

    /*name of restaurant*/
    private String name;

    /*address of restaurant*/
    private String address;

    /*menuLists of restaurant*/
    private Map<LocalDate, List<Dish>> menus;

    public Restaurant() {
    }

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<LocalDate, List<Dish>> getMenus() {
        return menus;
    }

    public void setMenus(Map<LocalDate, List<Dish>> menus) {
        this.menus = menus;
    }

    public void addNewDishItem(String name, double price){
        LocalDate today = LocalDate.now();
        List<Dish> todayMenu = this.menus.computeIfAbsent(today, k -> new ArrayList<>());
        Dish newDish = new Dish(name, price);
        todayMenu.add(newDish);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
