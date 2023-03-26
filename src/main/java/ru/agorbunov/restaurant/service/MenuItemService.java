package ru.agorbunov.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
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

@Service("DishDescriptionService")
public class MenuItemService extends BaseService<MenuItemRepository, MenuItem> {

    private final MenuListRepository menuListRepository;

    private final DishRepository dishRepository;


    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository, MenuListRepository menuListRepository, DishRepository dishRepository) {
        super(menuItemRepository);
        this.menuListRepository = menuListRepository;
        this.dishRepository = dishRepository;
    }

    public List<MenuItem> getByMenu(int id) {
        return repository.getByMenu(id);
    }

    public MenuItem update(MenuItem menuItem, int menuListId) {
        Assert.notNull(menuItem, "dishDescription must not be null");
        MenuList menuList = menuListRepository.get(menuListId);
        if (menuList == null) {
            throw new EntityNotFoundException("menuList is null!");
        }
        menuItem.setMenuList(menuList);
        return repository.save(menuItem);
    }

    public MenuItem update(MenuItem menuItem, int dishId,  int menuListId) {
        Assert.notNull(menuItem, "dishDescription must not be null");
        MenuList menuList = menuListRepository.get(menuListId);
        menuItem.setMenuList(menuList);
        Dish dish = dishRepository.get(dishId);
        menuItem.setDish(dish);
        return repository.save(menuItem);
    }

    @Transactional
    public void updateList(List<MenuItem> menuItemList, int menuListId) {
        Assert.notNull(menuItemList, "dishDescriptionList must not be null");
        MenuList menuList = menuListRepository.get(menuListId);
        for (MenuItem menuItem : menuItemList) {
            menuItem.setMenuList(menuList);
        }
        repository.saveAll(menuItemList);
    }
}
