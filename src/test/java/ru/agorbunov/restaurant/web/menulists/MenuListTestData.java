package ru.agorbunov.restaurant.web.menulists;

import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.web.testdata.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;

public class MenuListTestData {

    public static final MatcherFactory.Matcher<MenuList> MENULIST_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuList.class, "items");

    public static final MenuList MENU_LIST_01 = new MenuList(100010, LocalDate.of(2023, Month.MARCH, 8));
    public static final MenuList MENU_LIST_02 = new MenuList(100014, LocalDate.of(2022, Month.DECEMBER, 14));
    public static final MenuList MENU_LIST_03 = new MenuList(100018, LocalDate.of(2022, Month.DECEMBER, 15));
    public static final MenuList MENU_LIST_05 = new MenuList(100011, LocalDate.now());

    public static final int MENU_LIST_01_ID = 100010;
    public static final int MENU_LIST_02_ID = 100014;
    public static final int MENU_LIST_03_ID = 100018;
}
