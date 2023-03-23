package ru.agorbunov.restaurant.web.restaurant;

import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.util.JsonUtil;
import ru.agorbunov.restaurant.web.MatcherFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Admin on 21.01.2017.
 */
public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuLists");

    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("items", "menuLists.id" ).isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final Restaurant RESTAURANT_01 = new Restaurant(100006, "Ёлки-палки","ул. Некрасова, 14");
    public static final Restaurant RESTAURANT_02 = new Restaurant(100007, "Пиццерия","пл. Пушкина, 6");
    public static final Restaurant RESTAURANT_03 = new Restaurant(100008, "Закусочная","Привокзальная пл, 3");
    public static final Restaurant RESTAURANT_04 = new Restaurant("Прага","ул. Арбат, 1");

    public static final int RESTAURANT_01_ID = 100006;
    public static final int RESTAURANT_02_ID = 100007;
    public static final int RESTAURANT_03_ID = 100008;
    public static final int NOT_FOUND_ID = 100;

    public static Restaurant getUpdatedRestaurant() {
        return new Restaurant(100006, "Новое название","ул. Некрасова, 14");
    }

    public static Restaurant getNewRestaurant() {
        return new Restaurant("Некоторый ресторан","Некоторый адрес");
    }


    public static String jsonWithName(Restaurant restaurant, String name) {
        return JsonUtil.writeAdditionProps(restaurant, "name", name);
    }

}
