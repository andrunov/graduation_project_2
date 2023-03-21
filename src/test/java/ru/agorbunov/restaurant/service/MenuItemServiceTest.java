package ru.agorbunov.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.service.testdata.MenuItemTestData;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.util.List;

import static ru.agorbunov.restaurant.service.testdata.DishTestData.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MenuItemServiceTest {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private DishService dishService;

    @Test
    public void getByMenu() throws Exception {
        Assertions.assertEquals(5, menuItemService.getByMenu(MenuItemTestData.MENU_LIST_01_ID).size());
    }

    @Test
    public void getByMenuWithDish() throws Exception {
        List<MenuItem> list = menuItemService.getByMenu(MenuItemTestData.MENU_LIST_01_ID);
        for (MenuItem dd : list) {
            Assertions.assertNotNull(dd.getDish());
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
        dishDescr.setDish(dishService.get(DISH_05_ID));
        menuItemService.update(dishDescr, MenuItemTestData.MENU_LIST_02_ID);
        MenuItem dishDescrUpdated = menuItemService.get(DISH_DESCR_01_ID);
        MATCHER.assertEquals(DISH_05, dishDescrUpdated.getDish());
    }

    @Test
    public void updateList() throws Exception {
        List<MenuItem> list = menuItemService.getByMenu(MenuItemTestData.MENU_LIST_01_ID);
        for (MenuItem dd : list) {
            dd.setPrice(1.25);
        }
        menuItemService.updateList(list, MenuItemTestData.MENU_LIST_01_ID);
        List<MenuItem> updated = menuItemService.getByMenu(MenuItemTestData.MENU_LIST_01_ID);
        for (MenuItem dd : updated) {
            Assertions.assertEquals(1.25, dd.getPrice());
        }
    }

    @Test
    public void create() throws Exception{
        MenuItem menuItem = new MenuItem();
        menuItem.setDish(dishService.get(100028));
        menuItem.setPrice(2.5);
        menuItemService.update(menuItem, MenuItemTestData.MENU_LIST_02_ID);
        Assertions.assertEquals(6, menuItemService.getByMenu(MenuItemTestData.MENU_LIST_02_ID).size());
    }

    @Test
    public void saveNull() throws Exception {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            menuItemService.update(null, MenuItemTestData.MENU_LIST_02_ID);
        });
    }

    @Test
    public void delete() throws Exception {
        menuItemService.delete(DISH_DESCR_04_ID);
        Assertions.assertEquals(4, menuItemService.getByMenu(MenuItemTestData.MENU_LIST_01_ID).size());
    }

    @Test
    public void deleteNotFound() throws Exception {
        menuItemService.delete(10);
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("error message");
        });
    }


    @Test
    public void getNotFound() throws Exception {
        menuItemService.get(10);
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("error message");
        });
    }

}
