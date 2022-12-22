package ru.agorbunov.restaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.service.MenuListService;
import ru.agorbunov.restaurant.service.RestaurantService;
import ru.agorbunov.restaurant.util.DateTimeUtil;
import ru.agorbunov.restaurant.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Rest controller for menuLists.jsp and other .jsp
 * to exchange menuList data with menuListService-layer
 */
@RestController
@RequestMapping(value =  "/ajax/menuLists")
public class MenuListAjaxController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuListService menuListService;

    @Autowired
    private RestaurantService restaurantService;

    /*get all menu lists by current restaurant*/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuList> getByRestaurant() {
        log.info("getByRestaurant");
        Restaurant currentRestaurant = CurrentEntities.getCurrentRestaurant();
        return menuListService.getByRestaurant(currentRestaurant.getId());
    }

    /*get menuList by Id */
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuList getMenuList(@PathVariable("id") int id) {
        log.info("get " + id);
        return menuListService.get(id);
    }

    /*delete menuList by Id*/
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete " + id);
        menuListService.delete(id);
    }

    /*create new menuList or update if exist*/
    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("dateTime")@DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime dateTime){
        Restaurant currentRestaurant = CurrentEntities.getCurrentRestaurant();
        MenuList menuList = new MenuList();
        menuList.setDate(dateTime.toLocalDate());
        menuList.setId(id);
        checkEmpty(menuList);
        if (menuList.isNew()) {
            ValidationUtil.checkNew(menuList);
            log.info("create " + menuList);
            menuListService.update(menuList,currentRestaurant.getId());
        } else {
            log.info("update " + menuList);
            menuListService.update(menuList,currentRestaurant.getId());
        }
    }

    /*check menuList for empty fields*/
    private void checkEmpty(MenuList menuList){
        ValidationUtil.checkEmpty(menuList.getDate(),"date");
    }


}
