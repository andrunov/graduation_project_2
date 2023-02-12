package ru.agorbunov.restaurant.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.MenuItemTestData;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.agorbunov.restaurant.RestaurantTestData.*;

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
                Arrays.asList(RESTAURANT_01, RESTAURANT_02, RESTAURANT_03, RESTAURANT_04,  newRestaurant),
                restaurantService.getAll());
    }

    @Test
    public void saveWith() throws Exception {

        LocalDate now = LocalDate.now();
        MenuList menuList = new MenuList();
        menuList.setDate(now);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItemTestData.MENU_ITEM_01);
        menuItems.add(MenuItemTestData.MENU_ITEM_02);
        menuItems.add(MenuItemTestData.MENU_ITEM_03);
        menuItems.add(MenuItemTestData.MENU_ITEM_04);
        menuItems.add(MenuItemTestData.MENU_ITEM_05);
        menuList.setItems(menuItems);
        Restaurant newRestaurant = new Restaurant("Созданный ресторант","ул. Новая, 1");
        menuList.setRestaurant(newRestaurant);
        newRestaurant.setMenuLists(new ArrayList<>());
        newRestaurant.getMenuLists().add(menuList);
        restaurantService.update(newRestaurant);
        menuListService.update(menuList, newRestaurant.getId());
        MATCHER.assertCollectionEquals(
                Arrays.asList(RESTAURANT_01, RESTAURANT_02, RESTAURANT_03, RESTAURANT_04,  newRestaurant),
                restaurantService.getAll());
        Restaurant updated = restaurantService.getWithMenuLists(newRestaurant.getId());
        Assertions.assertEquals(1, updated.getMenuLists().size());

    }

    @Test
    public void saveNull() throws Exception {
        restaurantService.update(null);
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("error message");
        });
    }

    @Test
    public void delete() throws Exception {
        restaurantService.delete(RESTAURANT_02_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT_01, RESTAURANT_03, RESTAURANT_04), restaurantService.getAll());
    }


    @Test
    public void deleteNotFound() throws Exception {
        restaurantService.delete(10);
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("error message");
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
        restaurantService.get(10);
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
              throw new NotFoundException("error message");
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
        restaurantService.update(null);
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("error message");
        });

    }

    @Test
    public void getByVote() throws Exception {
        MATCHER.assertEquals(RESTAURANT_01, restaurantService.getByVote(100023));
    }


}
