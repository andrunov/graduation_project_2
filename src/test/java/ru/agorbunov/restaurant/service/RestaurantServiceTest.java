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
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.*;

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
        Assert.assertEquals(3, restaurant.getMenuLists().size());
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
        List<MenuItem> dishList = new ArrayList<>();
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_01);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_02);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_03);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_04);
        dishList.add(DishDescriptionTestData.DISH_DESCRPT_05);
        menuList.setItems(dishList);
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
        Assert.assertEquals(1, updated.getMenuLists().size());

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
