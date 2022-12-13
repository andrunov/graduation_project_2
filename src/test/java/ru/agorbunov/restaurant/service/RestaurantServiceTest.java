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
import ru.agorbunov.restaurant.model.DishDescription;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static ru.agorbunov.restaurant.RestaurantTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuListService menuListService;

    @Test
    public void getWith() throws Exception{
        Restaurant restaurant = restaurantService.getWithMenuLists(RESTAURANT_01_ID);
        Assert.assertEquals(1, restaurant.getMenuLists().size());
    }


    @Test
    public void save() throws Exception {
        restaurantService.update(RESTAURANT_CREATED);
        MATCHER.assertCollectionEquals(
                Arrays.asList(RESTAURANT_01, RESTAURANT_02, RESTAURANT_03, RESTAURANT_04,  RESTAURANT_CREATED),
                restaurantService.getAll());
    }

    @Test
    public void saveWith() throws Exception {

        LocalDate now = LocalDate.now();
        MenuList menuList = new MenuList();
        menuList.setDate(now);
        Set<DishDescription> dishList = new HashSet<>();
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_01);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_02);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_03);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_04);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_05);
        menuList.setDishList(dishList);
        RESTAURANT_CREATED.setMenuLists(new HashMap<>());
        RESTAURANT_CREATED.getMenuLists().put(now, menuList);
        restaurantService.update(RESTAURANT_CREATED);
        MATCHER.assertCollectionEquals(
                Arrays.asList(RESTAURANT_01, RESTAURANT_02, RESTAURANT_03, RESTAURANT_04,  RESTAURANT_CREATED),
                restaurantService.getAll());
        Restaurant updated = restaurantService.getWithMenuLists(RESTAURANT_CREATED_ID);
        Assert.assertEquals(updated.getMenu(now).getDishList().size(), 4);

    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        restaurantService.update(null);
    }

    @Test
    public void delete() throws Exception {
        restaurantService.delete(RESTAURANT_02_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT_01, RESTAURANT_03, RESTAURANT_04), restaurantService.getAll());
    }


    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        restaurantService.delete(10);
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

      @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        restaurantService.get(10);
    }

    @Test
    public void update() throws Exception{
        Restaurant restaurant = restaurantService.get(RESTAURANT_02_ID);
        restaurant.setAddress("обновленный адрес");
        restaurant.setName("обновленное имя");
        restaurantService.update(restaurant);
        Restaurant updated = restaurantService.get(RESTAURANT_02_ID);
        Assert.assertEquals(updated.getAddress(), "обновленный адрес");
        Assert.assertEquals(updated.getName(), "обновленное имя");
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateNull() throws Exception {
        restaurantService.update(null);
    }


}
