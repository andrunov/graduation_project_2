package ru.agorbunov.restaurant;

import ru.agorbunov.restaurant.matcher.ModelMatcher;
import ru.agorbunov.restaurant.model.Dish;


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
    public static final int DISH_01_ID = 100028;
    public static final int DISH_02_ID = 100029;
    public static final int DISH_03_ID = 100030;
    public static final int DISH_04_ID = 100031;
    public static final int DISH_05_ID = 100032;
    public static final int DISH_DESCR_01_ID = 100048;
    public static final int DISH_DESCR_04_ID = 100051;




}
