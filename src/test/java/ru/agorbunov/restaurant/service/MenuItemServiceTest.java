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
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.util.List;

import static ru.agorbunov.restaurant.DishTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB_test.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MenuItemServiceTest {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private DishService dishService;

    @Test
    public void getByMenu() throws Exception {
        Assert.assertEquals(5, menuItemService.getByMenu(MenuItemTestData.MENU_LIST_01_ID).size());
    }

    @Test
    public void getByMenuWithDish() throws Exception {
        List<MenuItem> list = menuItemService.getByMenu(MenuItemTestData.MENU_LIST_01_ID);
        for (MenuItem dd : list) {
            Assert.assertNotNull(dd.getDish());
        }
    }

    @Test
    public void getWithDish() throws Exception {
        MenuItem menuItem = menuItemService.get(DISH_DESCR_01_ID);
        MATCHER.assertEquals(DISH_01, menuItem.getDish());
    }

    @Test
    public void update() throws Exception{
        MenuItem dishDescr = menuItemService.get(DISH_DESCR_01_ID);
        dishDescr.setDish(dishService.get(100032));
        menuItemService.update(dishDescr, MenuItemTestData.MENU_LIST_02_ID);
        MenuItem dishDescrUpdated = menuItemService.get(DISH_DESCR_01_ID);
        MATCHER.assertEquals(DISH_05, dishDescrUpdated.getDish());
    }

    @Test
    public void create() throws Exception{
        MenuItem menuItem = new MenuItem();
        menuItem.setDish(dishService.get(100028));
        menuItemService.update(menuItem, MenuItemTestData.MENU_LIST_02_ID);
        Assert.assertEquals(6, menuItemService.getByMenu(MenuItemTestData.MENU_LIST_02_ID).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        menuItemService.update(null, MenuItemTestData.MENU_LIST_02_ID);
    }

    @Test
    public void delete() throws Exception {
        menuItemService.delete(DISH_DESCR_04_ID);
        Assert.assertEquals(4, menuItemService.getByMenu(MenuItemTestData.MENU_LIST_01_ID).size());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        menuItemService.delete(10);
    }


    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        menuItemService.get(10);
    }

}
