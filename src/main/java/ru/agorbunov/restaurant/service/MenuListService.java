package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.repository.MenuListRepository;
import ru.agorbunov.restaurant.repository.RestaurantRepository;
import ru.agorbunov.restaurant.util.DateTimeUtil;
import ru.agorbunov.restaurant.util.exception.UpdateException;

import java.time.LocalDate;
import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("menuListService")
public class MenuListService {

    private final MenuListRepository menuListRepository;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuListService(MenuListRepository menuListRepository, RestaurantRepository restaurantRepository) {
        this.menuListRepository = menuListRepository;
        this.restaurantRepository = restaurantRepository;
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

    public MenuList getByRestaurantIdAndDate(int id, LocalDate date) {
        return menuListRepository.getByRestaurantIdAndDate(id, date);
    }

    public void update(MenuList menuList, int restaurantId) {
        Assert.notNull(menuList, "menuList must not be null");
        MenuList saved = getByRestaurantIdAndDate(restaurantId, menuList.getDate());
        if ((saved != null) && (!saved.getId().equals(menuList.getId())) ) {
            Restaurant restaurant = restaurantRepository.get(restaurantId);
            throw new UpdateException("Menu list of restaurant =" + restaurant.getName() +
                                        " for date " + DateTimeUtil.toString(menuList.getDate()) +
                                        " has already present, saving operation of new menu list impossible");
        }
        menuListRepository.save(menuList, restaurantId);
    }
}
