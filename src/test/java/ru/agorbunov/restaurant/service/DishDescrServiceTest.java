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

import static ru.agorbunov.restaurant.DishTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DishDescrServiceTest {

    @Autowired
    private DishDescrService dishDescrService;

    @Test
    public void getByMenu() throws Exception {
        Assert.assertEquals(5, dishDescrService.getByMenu(DishDescriptionTestData.MENU_LIST_01_ID).size());
    }

    @Test
    public void getWithDish() throws Exception {
        DishDescription dishDescription = dishDescrService.get(DISH_DESCR_04_ID);
        MATCHER.assertEquals(DISH_04, dishDescription.getDish());
    }

    @Test
    public void update() throws Exception{
        DishDescription dishDescr = dishDescrService.get(DISH_DESCR_04_ID);
        dishDescr.setDish(DISH_05);
        dishDescrService.update(dishDescr);
        DishDescription dishDescrUpdated = dishDescrService.get(DISH_DESCR_04_ID);
        MATCHER.assertEquals(DISH_05, dishDescrUpdated.getDish());
    }
}
