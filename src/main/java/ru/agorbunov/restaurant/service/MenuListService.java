package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.repository.MenuListRepository;
import ru.agorbunov.restaurant.repository.UserRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("menuListService")
public class MenuListService {

    private final MenuListRepository menuListRepository;

    private final UserRepository userRepository;

    @Autowired
    public MenuListService(MenuListRepository menuListRepository, UserRepository userRepository) {
        this.menuListRepository = menuListRepository;
        this.userRepository = userRepository;
    }

     public void delete(int id, int userId) {
        userRepository.checkIsAdmin(userId);
        checkNotFoundWithId(menuListRepository.delete(id), id);
    }

    public MenuList get(int id) {
        return checkNotFoundWithId(menuListRepository.get(id), id);
    }

    public List<MenuList> getByRestaurant(int id) {
        return menuListRepository.getByRestaurant(id);
    }

    public void update(MenuList menuList, int restaurantId, int userId) {
        userRepository.checkIsAdmin(userId);
        Assert.notNull(menuList, "menuList must not be null");
        menuListRepository.save(menuList, restaurantId);
    }
}
