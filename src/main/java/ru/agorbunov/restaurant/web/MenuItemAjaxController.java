package ru.agorbunov.restaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.service.MenuItemService;
import ru.agorbunov.restaurant.service.RestaurantService;
import ru.agorbunov.restaurant.to.MenuItemTo;
import ru.agorbunov.restaurant.to.MenuListTo;
import ru.agorbunov.restaurant.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest controller for menuLists.jsp and other .jsp
 * to exchange menuList data with menuListService-layer
 */
@RestController
@RequestMapping(value =  "/ajax/menuItems")
public class MenuItemAjaxController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuItemService menuItemService;

    /*get all menu lists by current restaurant*/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemTo> getByMenuList() {
        log.info("getByMenuList");
        MenuListTo currentMenuListTo = CurrentEntities.getCurrentMenuListTo();
        List<MenuItem> menuItems = menuItemService.getByMenu(currentMenuListTo.getId());
        List<MenuItemTo> result = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            result.add(MenuItemTo.fromMenuItem(menuItem));
        }
        return result;
    }

    /*get menuList by Id */
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuItemTo getMenuItem(@PathVariable("id") int id) {
        log.info("get " + id);
        MenuItem menuItem = menuItemService.get(id);
        CurrentEntities.setCurrentDish(menuItem.getDish());
        return MenuItemTo.fromMenuItem(menuItem);
    }

    /*delete menuList by Id*/
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete " + id);
        menuItemService.delete(id);
    }

    /*create new menuList or update if exist*/
    @PostMapping
    public void createOrUpdate(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("dishId") Integer dishId,
                               @RequestParam("price") String price){
        int menuListId = CurrentEntities.getCurrentMenuListTo().getId();
        MenuItemTo menuItemTo = new MenuItemTo();
        menuItemTo.setId(id);
        menuItemTo.setPrice(Double.parseDouble(price));
        checkEmpty(menuItemTo);
        MenuItem menuItem = MenuItemTo.fromMenuItemTo(menuItemTo);
        menuItem.setDish(CurrentEntities.getCurrentDish());
        if (menuItem.isNew()) {
            ValidationUtil.checkNew(menuItem);
            log.info("create " + menuItem);
            menuItemService.update(menuItem, dishId, menuListId);
        } else {
            log.info("update " + menuItem);
            menuItemService.update(menuItem, dishId, menuListId);
        }
    }

    /*check menuList for empty fields*/
    private void checkEmpty(MenuItemTo menuItemTo){
        ValidationUtil.checkEmpty(menuItemTo.getPrice(),"price");
    }


}
