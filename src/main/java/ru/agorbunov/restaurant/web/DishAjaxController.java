package ru.agorbunov.restaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.service.DishService;
import ru.agorbunov.restaurant.service.RestaurantService;
import ru.agorbunov.restaurant.util.ValidationUtil;

import java.util.List;

/**
 * Rest controller for menuLists.jsp and other .jsp
 * to exchange menuList data with menuListService-layer
 */
@RestController
@RequestMapping(value =  "/ajax/dishes")
public class DishAjaxController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService dishService;

    /*get all menu lists by current restaurant*/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll() {
        log.info("getAll");
        return dishService.getAll();
    }

    /*get menuList by Id */
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getDish(@PathVariable("id") int id) {
        log.info("get " + id);
        return dishService.get(id);
    }

    /*delete menuList by Id*/
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete " + id);
        dishService.delete(id);
    }

    /*create new menuList or update if exist*/
    @PostMapping
    public void createOrUpdate(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("name") String name){
        Dish dish = new Dish();
        dish.setId(id);
        dish.setName(name);
        checkEmpty(dish);
        if (dish.isNew()) {
            ValidationUtil.checkNew(dish);
            log.info("create " + dish);
            dishService.update(dish);
        } else {
            log.info("update " + dish);
            dishService.update(dish);
        }
    }

    /*check menuList for empty fields*/
    private void checkEmpty(Dish dish){
        ValidationUtil.checkEmpty(dish.getName(),"name");
    }


}
