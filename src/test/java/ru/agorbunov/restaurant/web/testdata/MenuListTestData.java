package ru.agorbunov.restaurant.web.testdata;

import ru.agorbunov.restaurant.model.MenuList;

import java.time.LocalDate;
import java.time.Month;

public class MenuListTestData {

    public static final MenuList MENU_LIST_01 = new MenuList(100010, LocalDate.of(2023, Month.MARCH, 8));
    public static final MenuList MENU_LIST_02 = new MenuList(100014, LocalDate.of(2022, Month.DECEMBER, 14));
    public static final MenuList MENU_LIST_03 = new MenuList(100018, LocalDate.of(2022, Month.DECEMBER, 15));
}
