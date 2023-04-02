package ru.agorbunov.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.service.testdata.MenuItemTestData;
import ru.agorbunov.restaurant.service.testdata.RestaurantTestData;
import ru.agorbunov.restaurant.util.exception.IllegalRequestDataException;
import ru.agorbunov.restaurant.util.exception.NotFoundException;
import ru.agorbunov.restaurant.util.exception.UpdateException;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MenuListServiceTest {

    @Autowired
    private MenuListService menuListService;

    @Test
    public void getByRestaurant() throws Exception {
        Assertions.assertEquals(3, menuListService.getByRestaurant(RestaurantTestData.RESTAURANT_01_ID).size());
    }

    @Test
    public void saveNull() throws Exception {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            menuListService.update(MenuItemTestData.NOT_FOUND_MENU_ITEM_ID, LocalDate.now(), RestaurantTestData.RESTAURANT_02_ID);
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
        LocalDate date = LocalDate.of(2000, 01, 01);
        menuListService.update(MenuItemTestData.MENU_LIST_02_ID, date, RestaurantTestData.RESTAURANT_02_ID);
        MenuList updated = menuListService.get(MenuItemTestData.MENU_LIST_02_ID);
        Assertions.assertEquals(date, updated.getDate());
    }


    @Test
    public void updateNull() throws Exception {
        Assertions.assertThrows(IllegalRequestDataException.class, () -> {
            menuListService.update(MenuItemTestData.MENU_LIST_02_ID, null, RestaurantTestData.RESTAURANT_02_ID);
        });
    }

}
