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
import ru.agorbunov.restaurant.util.exception.NoRightsException;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.util.List;

import static ru.agorbunov.restaurant.DishTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DishDescriptionServiceTest {

    @Autowired
    private DishDescriptionService dishDescriptionService;

    @Test
    public void getByMenu() throws Exception {
        Assert.assertEquals(5, dishDescriptionService.getByMenu(DishDescriptionTestData.MENU_LIST_01_ID).size());
    }

    @Test
    public void getByMenuWithDish() throws Exception {
        List<DishDescription> list = dishDescriptionService.getByMenu(DishDescriptionTestData.MENU_LIST_01_ID);
        for (DishDescription dd : list) {
            Assert.assertNotNull(dd.getDish());
        }
    }

    @Test
    public void getWithDish() throws Exception {
        DishDescription dishDescription = dishDescriptionService.get(DISH_DESCR_04_ID);
        MATCHER.assertEquals(DISH_04, dishDescription.getDish());
    }

    @Test
    public void update() throws Exception{
        DishDescription dishDescr = dishDescriptionService.get(DISH_DESCR_04_ID);
        dishDescr.setDish(DISH_05);
        dishDescriptionService.update(dishDescr, DishDescriptionTestData.MENU_LIST_02_ID);
        DishDescription dishDescrUpdated = dishDescriptionService.get(DISH_DESCR_04_ID);
        MATCHER.assertEquals(DISH_05, dishDescrUpdated.getDish());
    }

    @Test
    public void create() throws Exception{
        dishDescriptionService.update(DishDescriptionTestData.DISH_DESCRPT_01, DishDescriptionTestData.MENU_LIST_02_ID);
        Assert.assertEquals(6, dishDescriptionService.getByMenu(DishDescriptionTestData.MENU_LIST_02_ID).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        dishDescriptionService.update(null, DishDescriptionTestData.MENU_LIST_02_ID);
    }

    @Test
    public void delete() throws Exception {
        dishDescriptionService.delete(DISH_DESCR_05_ID);
        Assert.assertEquals(4, dishDescriptionService.getByMenu(DishDescriptionTestData.MENU_LIST_02_ID).size());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        dishDescriptionService.delete(10);
    }


    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        dishDescriptionService.get(10);
    }

}
