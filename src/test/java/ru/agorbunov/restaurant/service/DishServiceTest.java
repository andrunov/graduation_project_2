package ru.agorbunov.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import static ru.agorbunov.restaurant.DishTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DishServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    public void save() throws Exception {
        dishService.update(DISH_CREATED);
        Assert.assertEquals(21, dishService.getAll().size());

    }


    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        dishService.update(null);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        dishService.delete(DISH_03_ID);
        dishService.get(DISH_03_ID);
    }


    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        dishService.delete(10);
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(20, dishService.getAll().size());
    }

    @Test
    public void get() throws Exception {
        Dish dish = dishService.get(DISH_01_ID);
        MATCHER.assertEquals(DISH_01, dish);
    }


    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        dishService.get(10);
    }

    @Test
    public void update() throws Exception{
        Dish dish = dishService.get(DISH_02_ID);
        dish.setName("обновленное название");
        dishService.update(dish);
        Assert.assertEquals(dish.getName(), "обновленное название");
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateNull() throws Exception {
        dishService.update(null);
    }


}
