package ru.agorbunov.restaurant.web.testdata;

import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.service.testdata.ModelMatcher;

/**
 * Created by Admin on 21.01.2017.
 */
public class RestaurantTestData {

    public static final ModelMatcher<Restaurant> MATCHER = new ModelMatcher<>();

    public static final Restaurant RESTAURANT_01 = new Restaurant("Ёлки-палки","ул. Некрасова, 14");
    public static final Restaurant RESTAURANT_02 = new Restaurant("Пиццерия","пл. Пушкина, 6");
    public static final Restaurant RESTAURANT_03 = new Restaurant("Закусочная","Привокзальная пл, 3");
    public static final Restaurant RESTAURANT_04 = new Restaurant("Прага","ул. Арбат, 1");

    public static final int RESTAURANT_01_ID = 100006;
    public static final int RESTAURANT_02_ID = 100007;
    public static final int RESTAURANT_03_ID = 100008;


}
