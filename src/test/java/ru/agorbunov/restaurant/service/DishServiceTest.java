package ru.agorbunov.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import static ru.agorbunov.restaurant.DishTestData.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    public void save() throws Exception {
        service.update(DISH_CREATED);
        Assertions.assertEquals(21, service.getAll().size());

    }


    @Test
    public void saveNull() throws Exception {
        service.update(null);
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("error message");
        });
    }

    @Test
    public void delete() throws Exception {
        service.delete(DISH_03_ID);
        service.get(DISH_03_ID);
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("error message");
        });
    }


    @Test
    public void deleteNotFound() throws Exception {
        service.delete(10);
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("error message");
        });
    }

    @Test
    public void getAll() throws Exception {
        Assertions.assertEquals(20, service.getAll().size());
    }

    @Test
    public void get() throws Exception {
        Dish dish = service.get(DISH_01_ID);
        MATCHER.assertEquals(DISH_01, dish);
    }


    @Test
    public void getNotFound() throws Exception {
        service.get(10);
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("error message");
        });
    }

    @Test
    public void update() throws Exception{
        Dish dish = service.get(DISH_02_ID);
        dish.setName("обновленное название");
        service.update(dish);
        Assertions.assertEquals(dish.getName(), "обновленное название");
    }

    @Test
    public void updateNull() throws Exception {
        service.update(null);
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("error message");
        });
    }


}
