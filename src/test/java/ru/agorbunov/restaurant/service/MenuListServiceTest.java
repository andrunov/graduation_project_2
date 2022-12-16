package ru.agorbunov.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agorbunov.restaurant.DishDescriptionTestData;
import ru.agorbunov.restaurant.RestaurantTestData;
import ru.agorbunov.restaurant.UserTestData;
import ru.agorbunov.restaurant.model.DishDescription;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.util.exception.NoRightsException;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MenuListServiceTest {

    @Autowired
    private MenuListService menuListService;

    @Autowired
    private DishDescriptionService dishDescriptionService;

    @Test
    public void getByRestaurant() throws Exception {
        Assert.assertEquals(1, menuListService.getByRestaurant(RestaurantTestData.RESTAURANT_01_ID).size());
    }

    @Test
    public void saveWith() throws Exception {
        LocalDate now = LocalDate.now();
        MenuList menuList = new MenuList();
        menuList.setDate(now);
        List<DishDescription> dishList = new ArrayList<>();
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_01);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_02);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_03);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_04);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_05);
        menuList.setDishList(dishList);
        menuListService.update(menuList, RestaurantTestData.RESTAURANT_01_ID, UserTestData.USER_01_ID);
        dishDescriptionService.updateList(menuList.getDishList(), menuList.getId());
        MenuList updated = menuListService.get(menuList.getId());
        Assert.assertEquals(RestaurantTestData.RESTAURANT_01.getAddress(), updated.getRestaurant().getAddress());
        List<DishDescription> updatedDDList = dishDescriptionService.getByMenu(menuList.getId());
        Assert.assertEquals(5, updatedDDList.size());

    }


    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        menuListService.update(null, RestaurantTestData.RESTAURANT_02_ID, UserTestData.USER_01_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        menuListService.delete(DishDescriptionTestData.MENU_LIST_01_ID, UserTestData.USER_01_ID);
        menuListService.get(DishDescriptionTestData.MENU_LIST_01_ID);
    }

    @Test(expected = NoRightsException.class)
    public void deleteNoRights() throws Exception {
        menuListService.delete(DishDescriptionTestData.MENU_LIST_01_ID, UserTestData.USER_00_ID);
    }


    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        menuListService.delete(10, UserTestData.USER_01_ID);
    }


    @Test
    public void get() throws Exception {
        MenuList menuList = menuListService.get(DishDescriptionTestData.MENU_LIST_02_ID);
        Assert.assertEquals(100011, (int) menuList.getId());
    }


    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        menuListService.get(10);
    }

    @Test
    public void update() throws Exception{
        MenuList menuList = menuListService.get(DishDescriptionTestData.MENU_LIST_02_ID);
        LocalDate date = LocalDate.of(2000, 01, 01);
        menuList.setDate(date);
        menuListService.update(menuList, menuList.getRestaurant().getId(), UserTestData.USER_01_ID);
        MenuList updated = menuListService.get(menuList.getId());
        Assert.assertEquals(date, updated.getDate());
    }

    @Test(expected = NoRightsException.class)
    public void updateNoRights() throws Exception{
        MenuList menuList = menuListService.get(DishDescriptionTestData.MENU_LIST_02_ID);
        LocalDate date = LocalDate.of(2000, 01, 01);
        menuList.setDate(date);
        menuListService.update(menuList, menuList.getRestaurant().getId(), UserTestData.USER_00_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNull() throws Exception {
        menuListService.update(null, RestaurantTestData.RESTAURANT_02_ID, UserTestData.USER_01_ID);
    }


}
