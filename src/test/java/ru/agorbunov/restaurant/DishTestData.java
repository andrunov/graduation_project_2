package ru.agorbunov.restaurant;

import ru.agorbunov.restaurant.matcher.ModelMatcher;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.model.User;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Admin on 20.01.2017.
 */
public class DishTestData {

    public static final ModelMatcher<Dish> MATCHER = new ModelMatcher<>();

    public static final Dish DISH_01 = new Dish("Каша овсяная");
    public static final Dish DISH_02 = new Dish("Сырники");
    public static final Dish DISH_03 = new Dish("Блины");
    public static final Dish DISH_04 = new Dish("Суп гороховый");
    public static final Dish DISH_05 = new Dish("Рассольник");


    public static final Dish DISH_CREATED = new Dish("Созданная еда");
    public static final int DISH_01_ID = 100018;
    public static final int DISH_02_ID = 100019;
    public static final int DISH_03_ID = 100020;
    public static final int DISH_04_ID = 100021;
    public static final int DISH_05_ID = 100022;
    public static final int DISH_DESCR_04_ID = 100041;
    public static final int DISH_DESCR_05_ID = 100045;




}
