package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.repository.MenuListRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("menuListService")
public class MenuListService {

    private final MenuListRepository repository;

    @Autowired
    public MenuListService(MenuListRepository repository) {
        this.repository = repository;
    }

    public MenuList create(MenuList menuList, int restaurantId) {
        Assert.notNull(menuList, "menuList must not be null");
        return repository.save(menuList, restaurantId);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public MenuList get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<MenuList> getByRestaurant(int id) {
        return repository.getByRestaurant(id);
    }

    public void update(MenuList menuList, int restaurantId) {
        Assert.notNull(menuList, "menuList must not be null");
        repository.save(menuList, restaurantId);
    }
}
