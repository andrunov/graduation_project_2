package ru.agorbunov.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agorbunov.restaurant.MenuItemTestData;
import ru.agorbunov.restaurant.RestaurantTestData;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.util.exception.NotFoundException;
import ru.agorbunov.restaurant.util.exception.UpdateException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB_test.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MenuListServiceTest {

    @Autowired
    private MenuListService menuListService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private DishService dishService;

    @Test
    public void getByRestaurant() throws Exception {
        Assert.assertEquals(3, menuListService.getByRestaurant(RestaurantTestData.RESTAURANT_01_ID).size());
    }

    @Test
    public void saveWith() throws Exception {
        LocalDate now = LocalDate.now().plusDays(1);
        MenuList menuList = new MenuList();
        menuList.setDate(now);
        List<MenuItem> dishList = new ArrayList<>();
        dishList.add(new MenuItem(null, dishService.get(100030), 1.25));
        dishList.add(new MenuItem(null, dishService.get(100031), 1.26));
        dishList.add(new MenuItem(null, dishService.get(100032), 3.25));
        dishList.add(new MenuItem(null, dishService.get(100033), 4.25));
        dishList.add(new MenuItem(null, dishService.get(100034), 5.25));
        menuList.setItems(dishList);
        menuListService.update(menuList, RestaurantTestData.RESTAURANT_01_ID);
        menuItemService.updateList(menuList.getItems(), menuList.getId());
        MenuList updated = menuListService.get(menuList.getId());
        Assert.assertEquals(RestaurantTestData.RESTAURANT_01.getAddress(), updated.getRestaurant().getAddress());
        List<MenuItem> updatedDDList = menuItemService.getByMenu(menuList.getId());
        Assert.assertEquals(5, updatedDDList.size());

    }


    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        menuListService.update(null, RestaurantTestData.RESTAURANT_02_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        menuListService.delete(MenuItemTestData.MENU_LIST_01_ID);
        menuListService.get(MenuItemTestData.MENU_LIST_01_ID);
    }


    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        menuListService.delete(10);
    }


    @Test
    public void get() throws Exception {
        MenuList menuList = menuListService.get(MenuItemTestData.MENU_LIST_02_ID);
        Assert.assertEquals(100011, (int) menuList.getId());
    }


    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        menuListService.get(10);
    }

    @Test
    public void update() throws Exception{
        MenuList menuList = menuListService.get(MenuItemTestData.MENU_LIST_02_ID);
        LocalDate date = LocalDate.of(2000, 01, 01);
        menuList.setDate(date);
        menuListService.update(menuList, menuList.getRestaurant().getId());
        MenuList updated = menuListService.get(menuList.getId());
        Assert.assertEquals(date, updated.getDate());
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateNull() throws Exception {
        menuListService.update(null, RestaurantTestData.RESTAURANT_02_ID);
    }

    @Test(expected = UpdateException.class)
    public void saveWithSameTate() throws Exception{
        LocalDate now = LocalDate.now();
        MenuList menuList = new MenuList();
        menuList.setDate(now);
        menuListService.update(menuList, RestaurantTestData.RESTAURANT_01_ID);
    }


}
