package ru.agorbunov.restaurant.web.menulists;

import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.util.JsonUtil;
import ru.agorbunov.restaurant.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;

public class MenuListTestData {

    public static final MatcherFactory.Matcher<MenuList> MENULIST_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuList.class, "items", "restaurant.menuLists");

    public static final MenuList MENU_LIST_01 = new MenuList(100010, LocalDate.of(2023, Month.MARCH, 8));
    public static final MenuList MENU_LIST_02 = new MenuList(100014, LocalDate.of(2022, Month.DECEMBER, 14));
    public static final MenuList MENU_LIST_03 = new MenuList(100018, LocalDate.of(2022, Month.DECEMBER, 15));
    public static final MenuList MENU_LIST_05 = new MenuList(100011, LocalDate.now());

    public static MenuList getUpdatedMenuList() {
        return new MenuList(100010, LocalDate.of(2023, Month.MARCH, 10));
    }

    public static final int MENU_LIST_01_ID = 100010;
    public static final int MENU_LIST_02_ID = 100014;
    public static final int MENU_LIST_03_ID = 100018;
    public static final int MENU_LIST_05_ID = 100011;
    public static final int NOT_FOUND_MENU_LIST_ID = 1245587;

    public static String jsonWithDate(MenuList menuList, LocalDate date) {
        return JsonUtil.writeAdditionProps(menuList, "date", date);
    }
}
