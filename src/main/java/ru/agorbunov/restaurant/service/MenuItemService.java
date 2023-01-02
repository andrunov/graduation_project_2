package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.repository.MenuItemRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("DishDescriptionService")
public class MenuItemService {

    private final MenuItemRepository repository;


    @Autowired
    public MenuItemService(MenuItemRepository repository) {
        this.repository = repository;
    }


    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public MenuItem get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<MenuItem> getByMenu(int id) {
        return repository.getByMenu(id);
    }

    public void update(MenuItem menuItem, int menuListId) {
        Assert.notNull(menuItem, "dishDescription must not be null");
        repository.save(menuItem, menuListId);
    }

    public void update(MenuItem menuItem, int dishId,  int menuListId) {
        Assert.notNull(menuItem, "dishDescription must not be null");
        repository.save(menuItem, dishId, menuListId);
    }

    public void updateList(List<MenuItem> menuItemList, int menuListId) {
        Assert.notNull(menuItemList, "dishDescriptionList must not be null");
        repository.saveList(menuItemList, menuListId);
    }
}
