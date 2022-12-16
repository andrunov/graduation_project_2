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

    private final MenuListRepository menuListRepository;

    @Autowired
    public MenuListService(MenuListRepository menuListRepository) {
        this.menuListRepository = menuListRepository;
    }

     public void delete(int id) {
        checkNotFoundWithId(menuListRepository.delete(id), id);
    }

    public MenuList get(int id) {
        return checkNotFoundWithId(menuListRepository.get(id), id);
    }

    public List<MenuList> getByRestaurant(int id) {
        return menuListRepository.getByRestaurant(id);
    }

    public void update(MenuList menuList, int restaurantId) {
        Assert.notNull(menuList, "menuList must not be null");
        menuListRepository.save(menuList, restaurantId);
    }
}
