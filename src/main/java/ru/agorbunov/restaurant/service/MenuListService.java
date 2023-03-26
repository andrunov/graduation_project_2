package ru.agorbunov.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
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

@Service("menuListService")
public class MenuListService extends BaseService<MenuListRepository, MenuList> {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuListService(MenuListRepository menuListRepository, RestaurantRepository restaurantRepository) {
        super(menuListRepository);
        this.restaurantRepository = restaurantRepository;
    }

    public List<MenuList> getByRestaurant(int id) {
        return repository.getByRestaurant(id);
    }

    public MenuList getByRestaurantIdAndDate(int id, LocalDate date) {
        return repository.getByRestaurantIdAndDate(id, date);
    }

    public MenuList update(MenuList menuList, int restaurantId) {
        Assert.notNull(menuList, "menuList must not be null");
        MenuList saved = getByRestaurantIdAndDate(restaurantId, menuList.getDate());
        if ((saved != null) && (!saved.getId().equals(menuList.getId())) ) {
            Restaurant restaurant = restaurantRepository.get(restaurantId);
            throw new UpdateException("Menu list of restaurant =" + restaurant.getName() +
                                        " for date " + DateTimeUtil.toString(menuList.getDate()) +
                                        " has already present, saving operation of new menu list impossible");
        }
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        if (restaurant == null) {
            throw new EntityNotFoundException("Not exist restaurant with ID=" + restaurantId);
        }
        menuList.setRestaurant(restaurant);
        return repository.save(menuList);
    }

}
