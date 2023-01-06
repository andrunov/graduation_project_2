package ru.agorbunov.restaurant.to;

import ru.agorbunov.restaurant.model.MenuItem;

public class MenuItemTo {

    public static MenuItemTo fromMenuItem(MenuItem source) {
        MenuItemTo result = new MenuItemTo();
        result.id = source.getId();
        result.dishId = source.getDish().getId();
        result.name = source.getDish().getName();
        result.price = source.getPrice();
        return result;
    }

    public static MenuItem fromMenuItemTo(MenuItemTo source) {
        MenuItem result = new MenuItem();
        result.setId(source.getId());
        result.setPrice(source.getPrice());
        return result;
    }

    public MenuItemTo() {
    }

    private Integer id;

    private Integer dishId;
    private String name;
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
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
}
