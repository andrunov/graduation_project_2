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
import ru.agorbunov.restaurant.util.exception.IllegalRequestDataException;

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


    public void updatePrice(int id, double newPrice) {
        this.getExisted(id); //check existed
        checkPrice(newPrice);
        repository.updateDateTime(id, newPrice);
    }

    public MenuItem update(int id, double price, int dishId,  int menuListId) {
        MenuItem menuItem = repository.get(id);
        menuItem.setPrice(price);
        Assert.notNull(menuItem, "dishDescription must not be null");
        MenuList menuList = menuListRepository.get(menuListId);
        menuItem.setMenuList(menuList);
        Dish dish = dishRepository.get(dishId);
        menuItem.setDish(dish);
        checkFields(menuItem);
        return repository.save(menuItem);
    }

    public MenuItem create( double price, int dishId,  int menuListId) {
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(price);
        MenuList menuList = menuListRepository.get(menuListId);
        menuItem.setMenuList(menuList);
        Dish dish = dishRepository.get(dishId);
        menuItem.setDish(dish);
        checkFields(menuItem);
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

    private void checkFields(MenuItem menuItem) {
        if (menuItem.getMenuList() == null) {
            throw new IllegalRequestDataException("MenuList must be presented");

        } else if (menuItem.getDish() == null) {
            throw new IllegalRequestDataException("MenuList must be presented");

        } else checkPrice(menuItem.getPrice());
    }

    private void checkPrice (double price) {
        if (price == 0) {
            throw new IllegalRequestDataException("MenuList must have price > 0");
        }
    }
}
