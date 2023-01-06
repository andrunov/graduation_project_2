package ru.agorbunov.restaurant.web;

import org.springframework.stereotype.Component;
import ru.agorbunov.restaurant.model.*;
import ru.agorbunov.restaurant.to.MenuListTo;

/**
 * Current entities of session
 */
@Component
public class CurrentEntities {

    private User currentUser;

    private Restaurant currentRestaurant;

    private MenuListTo currentMenuListTo;

    private Dish currentDish;

    public CurrentEntities() {
    }

    public Restaurant getCurrentRestaurant() {
        return currentRestaurant;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentRestaurant(Restaurant currentRestaurant) {
        this.currentRestaurant = currentRestaurant;
    }

    public MenuListTo getCurrentMenuListTo() {
        return currentMenuListTo;
    }

    public void setCurrentMenuListTo(MenuListTo currentMenuListTo) {
        this.currentMenuListTo = currentMenuListTo;
    }


    public Dish getCurrentDish() {
        return currentDish;
    }

    public void setCurrentDish(Dish currentDish) {
        this.currentDish = currentDish;
    }
}
