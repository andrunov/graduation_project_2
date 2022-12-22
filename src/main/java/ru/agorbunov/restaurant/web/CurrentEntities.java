package ru.agorbunov.restaurant.web;

import ru.agorbunov.restaurant.model.*;
import ru.agorbunov.restaurant.to.MenuListTo;

/**
 * Current entities of session
 */
class CurrentEntities {

    private static User currentUser;

    private static Restaurant currentRestaurant;

    private static MenuListTo currentMenuListTo;

    private static Dish currentDish;

    public CurrentEntities() {
    }

    static Restaurant getCurrentRestaurant() {
        return currentRestaurant;
    }

    static User getCurrentUser() {
        return currentUser;
    }

    static void setCurrentUser(User currentUser) {
        CurrentEntities.currentUser = currentUser;
    }

    static void setCurrentRestaurant(Restaurant currentRestaurant) {
        CurrentEntities.currentRestaurant = currentRestaurant;
    }

    static MenuListTo getCurrentMenuListTo() {
        return currentMenuListTo;
    }

    static void setCurrentMenuListTo(MenuListTo currentMenuListTo) {
        CurrentEntities.currentMenuListTo = currentMenuListTo;
    }


    static Dish getCurrentDish() {
        return currentDish;
    }

    static void setCurrentDish(Dish currentDish) {
        CurrentEntities.currentDish = currentDish;
    }
}
