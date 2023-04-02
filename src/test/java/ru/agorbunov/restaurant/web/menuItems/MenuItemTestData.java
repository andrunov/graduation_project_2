package ru.agorbunov.restaurant.web.menuItems;

import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.service.testdata.DishTestData;
import ru.agorbunov.restaurant.util.JsonUtil;
import ru.agorbunov.restaurant.web.MatcherFactory;

public class MenuItemTestData {

    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "dish", "menuList");


    public static final int MENU_LIST_01_ID = 100010;
    public static final int MENU_LIST_02_ID = 100011;

    public static final int MENU_ITEM_01_ID = 1;
    public static final int MENU_ITEM_02_ID = 2;
    public static final int MENU_ITEM_03_ID = 3;
    public static final int MENU_ITEM_04_ID = 4;
    public static final int MENU_ITEM_05_ID = 5;
    public static final int NOT_FOUND_MENU_ITEM_ID = 958456;

     public static MenuItem getUpdatedMenuItem() {
       return new MenuItem(1,null, null,1.48);
    }

    public static final MenuItem MENU_ITEM_01 = new MenuItem(1,null, null,1.25);
    public static final MenuItem MENU_ITEM_02 = new MenuItem(2,null, DishTestData.DISH_02,3.45);
    public static final MenuItem MENU_ITEM_03 = new MenuItem(3,null, DishTestData.DISH_03,2.48);
    public static final MenuItem MENU_ITEM_04 = new MenuItem(4,null, DishTestData.DISH_04,5.57);
    public static final MenuItem MENU_ITEM_05 = new MenuItem(5,null, DishTestData.DISH_05,6.87);

    public static String jsonWithPrice(MenuItem menuItem, double price) {
        return JsonUtil.writeAdditionProps(menuItem, "price", price);
    }

}
