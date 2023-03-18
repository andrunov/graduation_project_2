package ru.agorbunov.restaurant.web.dish;

import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.util.JsonUtil;
import ru.agorbunov.restaurant.web.testdata.MatcherFactory;

import java.util.List;


/**
 * Created by Admin on 20.01.2017.
 */
public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);
    public static final MatcherFactory.Matcher<List> DISHLIST_MATCHER = MatcherFactory.usingEqualsComparator(List.class);


    public static final Dish DISH_01 = new Dish(100022, "Каша овсяная");
    public static final Dish DISH_02 = new Dish(100023,"Сырники");
    public static final Dish DISH_03 = new Dish(100024,"Блины");
    public static final Dish DISH_04 = new Dish(100025,"Суп гороховый");
    public static final Dish DISH_05 = new Dish(100026,"Рассольник");


    public static final Dish DISH_CREATED = new Dish("Созданная еда");

    public static  Dish getUpdatedDish() {
        return new Dish(100024,"Новое название");
    }
    public static final int DISH_01_ID = 100022;
    public static final int DISH_02_ID = 100023;
    public static final int DISH_03_ID = 100024;
    public static final int DISH_04_ID = 100025;
    public static final int DISH_05_ID = 100026;
    public static final int NOT_FOUND_ID = 1;
    public static final int DISH_DESCR_01_ID = 1;
    public static final int DISH_DESCR_04_ID = 4;

    public static String jsonWithName(Dish dish, String name) {
        return JsonUtil.writeAdditionProps(dish, "name", name);
    }



}
