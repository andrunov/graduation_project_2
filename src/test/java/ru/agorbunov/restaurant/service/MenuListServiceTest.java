package ru.agorbunov.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.service.testdata.MenuItemTestData;
import ru.agorbunov.restaurant.service.testdata.RestaurantTestData;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.util.exception.NotFoundException;
import ru.agorbunov.restaurant.util.exception.UpdateException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MenuListServiceTest {

    @Autowired
    private MenuListService menuListService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private DishService dishService;

    @Test
    public void getByRestaurant() throws Exception {
        Assertions.assertEquals(3, menuListService.getByRestaurant(RestaurantTestData.RESTAURANT_01_ID).size());
    }

    @Test
    public void saveNull() throws Exception {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            menuListService.update(null, RestaurantTestData.RESTAURANT_02_ID);
        });
    }

    @Test
    public void delete() throws Exception {
        menuListService.delete(MenuItemTestData.MENU_LIST_01_ID);
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            menuListService.get(MenuItemTestData.MENU_LIST_01_ID);
        });
    }


    @Test
    public void deleteNotFound() throws Exception {
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            menuListService.delete(10);
        });
    }


    @Test
    public void get() throws Exception {
        MenuList menuList = menuListService.get(MenuItemTestData.MENU_LIST_02_ID);
        Assertions.assertEquals(100011, (int) menuList.getId());
    }


    @Test
    public void getNotFound() throws Exception {
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            menuListService.get(10);
        });
    }

    @Test
    public void update() throws Exception{
        MenuList menuList = menuListService.get(MenuItemTestData.MENU_LIST_02_ID);
        LocalDate date = LocalDate.of(2000, 01, 01);
        menuList.setDate(date);
        menuListService.update(menuList, menuList.getRestaurant().getId());
        MenuList updated = menuListService.get(menuList.getId());
        Assertions.assertEquals(date, updated.getDate());
    }


    @Test
    public void updateNull() throws Exception {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            menuListService.update(null, RestaurantTestData.RESTAURANT_02_ID);
        });
    }

    @Test
    public void saveWithSameDate() throws Exception{
        LocalDate now = LocalDate.now();
        MenuList menuList = new MenuList();
        menuList.setDate(now);
        Throwable exception = Assertions.assertThrows(UpdateException.class, () -> {
            menuListService.update(menuList, RestaurantTestData.RESTAURANT_02_ID);
        });
    }


}
