package ru.agorbunov.restaurant.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.service.testdata.MenuItemTestData;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.util.exception.IllegalRequestDataException;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.agorbunov.restaurant.service.testdata.RestaurantTestData.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuListService menuListService;

    @Test
    public void getWith() throws Exception{
        Restaurant restaurant = restaurantService.getWithMenuLists(RESTAURANT_01_ID);
        Assertions.assertEquals(3, restaurant.getMenuLists().size());
    }


    @Test
    public void save() throws Exception {
        Restaurant newRestaurant = new Restaurant("Созданный ресторант","ул. Новая, 1");
        restaurantService.update(newRestaurant);
        MATCHER.assertCollectionEquals(
                Arrays.asList(newRestaurant, RESTAURANT_01, RESTAURANT_02, RESTAURANT_03, RESTAURANT_04),
                restaurantService.getAll());
    }

    @Test
    public void saveWith() throws Exception {
        Restaurant newRestaurant = new Restaurant("Созданный ресторант","ул. Новая, 1");
        Restaurant saved = restaurantService.update(newRestaurant);
        menuListService.create( LocalDate.now(), saved.getId());
        MATCHER.assertCollectionEquals(
                Arrays.asList(newRestaurant, RESTAURANT_01, RESTAURANT_02, RESTAURANT_03, RESTAURANT_04),
                restaurantService.getAll());
        Restaurant updated = restaurantService.getWithMenuLists(newRestaurant.getId());
        Assertions.assertEquals(1, updated.getMenuLists().size());

    }

    @Test
    public void saveNull() throws Exception {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            restaurantService.update(null);
        });
    }

    @Test
    public void delete() throws Exception {
        restaurantService.delete(RESTAURANT_02_ID);
        MATCHER.assertCollectionEquals(
                    Arrays.asList(RESTAURANT_01, RESTAURANT_03, RESTAURANT_04),
                    restaurantService.getAll());
    }


    @Test
    public void deleteNotFound() throws Exception {
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            restaurantService.delete(10);
        });
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT_01, RESTAURANT_02, RESTAURANT_03, RESTAURANT_04), restaurantService.getAll());

    }

    @Test
    public void get() throws Exception {
        Restaurant restaurant = restaurantService.get(RESTAURANT_02_ID);
        MATCHER.assertEquals(RESTAURANT_02, restaurant);
    }

    @Test
    public void getNotFound() throws Exception {
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            restaurantService.get(10);
        });
    }

    @Test
    public void update() throws Exception{
        Restaurant restaurant = restaurantService.get(RESTAURANT_02_ID);
        restaurant.setAddress("обновленный адрес");
        restaurant.setName("обновленное имя");
        restaurantService.update(restaurant);
        Restaurant updated = restaurantService.get(RESTAURANT_02_ID);
        Assertions.assertEquals(updated.getAddress(), "обновленный адрес");
        Assertions.assertEquals(updated.getName(), "обновленное имя");
    }


    @Test
    public void updateNull() throws Exception {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            restaurantService.update(null);
        });

    }

    @Test
    public void getByVote() throws Exception {
        MATCHER.assertEquals(RESTAURANT_01, restaurantService.getByVote(1));
    }


}
