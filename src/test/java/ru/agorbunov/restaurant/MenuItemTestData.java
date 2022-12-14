package ru.agorbunov.restaurant;

import ru.agorbunov.restaurant.matcher.ModelMatcher;
import ru.agorbunov.restaurant.model.MenuItem;

public class MenuItemTestData {

    public static final ModelMatcher<MenuItem> MATCHER = new ModelMatcher<>();

    public static final int MENU_LIST_01_ID = 100010;
    public static final int MENU_LIST_02_ID = 100011;

    public static final MenuItem MENU_ITEM_01 = new MenuItem(null, DishTestData.DISH_01,1.25);
    public static final MenuItem MENU_ITEM_02 = new MenuItem(null, DishTestData.DISH_02,3.45);
    public static final MenuItem MENU_ITEM_03 = new MenuItem(null, DishTestData.DISH_03,2.48);
    public static final MenuItem MENU_ITEM_04 = new MenuItem(null, DishTestData.DISH_04,5.57);
    public static final MenuItem MENU_ITEM_05 = new MenuItem(null, DishTestData.DISH_05,6.87);



}
