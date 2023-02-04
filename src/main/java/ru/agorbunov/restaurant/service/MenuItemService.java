package ru.agorbunov.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.repository.DishRepository;
import ru.agorbunov.restaurant.repository.MenuItemRepository;
import ru.agorbunov.restaurant.repository.MenuListRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("DishDescriptionService")
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    private final MenuListRepository menuListRepository;

    private final DishRepository dishRepository;


    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository, MenuListRepository menuListRepository, DishRepository dishRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuListRepository = menuListRepository;
        this.dishRepository = dishRepository;
    }


    public void delete(int id) {
        checkNotFoundWithId(menuItemRepository.delete(id), id);
    }

    public MenuItem get(int id) {
        return checkNotFoundWithId(menuItemRepository.get(id), id);
    }

    public List<MenuItem> getByMenu(int id) {
        return menuItemRepository.getByMenu(id);
    }

    public void update(MenuItem menuItem, int menuListId) {
        Assert.notNull(menuItem, "dishDescription must not be null");
        MenuList menuList = menuListRepository.get(menuListId);
        menuItem.setMenuList(menuList);
        menuItemRepository.save(menuItem);
    }

    public void update(MenuItem menuItem, int dishId,  int menuListId) {
        Assert.notNull(menuItem, "dishDescription must not be null");
        MenuList menuList = menuListRepository.get(menuListId);
        menuItem.setMenuList(menuList);
        Dish dish = dishRepository.get(dishId);
        menuItem.setDish(dish);
        menuItemRepository.save(menuItem);
    }

    @Transactional
    public void updateList(List<MenuItem> menuItemList, int menuListId) {
        Assert.notNull(menuItemList, "dishDescriptionList must not be null");
        MenuList menuList = menuListRepository.get(menuListId);
        for (MenuItem menuItem : menuItemList) {
            menuItem.setMenuList(menuList);
            menuItemRepository.save(menuItem);
        }
    }
}
