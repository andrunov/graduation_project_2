package ru.agorbunov.restaurant.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.repository.MenuItemRepository;
import ru.agorbunov.restaurant.repository.MenuListRepository;
import ru.agorbunov.restaurant.repository.RestaurantRepository;
import ru.agorbunov.restaurant.util.DateTimeUtil;
import ru.agorbunov.restaurant.util.exception.IllegalRequestDataException;
import ru.agorbunov.restaurant.util.exception.UpdateException;

import java.time.LocalDate;
import java.util.List;

@Service("menuListService")
public class MenuListService extends BaseService<MenuListRepository, MenuList> {

    private final RestaurantRepository restaurantRepository;

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuListService(MenuListRepository menuListRepository, RestaurantRepository restaurantRepository, MenuListRepository menuItemRepository, MenuItemRepository menuItemRepository1) {
        super(menuListRepository);
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository1;
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

    public MenuList create(LocalDate date, int restaurantId) {
        MenuList menuList = new MenuList();
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        if (restaurant == null) {
            throw new EntityNotFoundException("Not exist restaurant with ID=" + restaurantId);
        }
        menuList.setDate(date);
        menuList.setRestaurant(restaurant);
        checkFields(menuList);
        return repository.save(menuList);
    }

    public MenuList update(int id, LocalDate date, int restaurantId) {
        MenuList menuList = repository.get(id);
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        if (restaurant == null) {
            throw new EntityNotFoundException("Not exist restaurant with ID=" + restaurantId);
        }
        menuList.setDate(date);
        menuList.setRestaurant(restaurant);
        checkFields(menuList);
        return repository.save(menuList);
    }

    private void checkFields(MenuList menuList) {
        if (menuList.getRestaurant() == null) {
            throw new IllegalRequestDataException("Restaurant must be presented");
        } else if (menuList.getDate() == null) {
            throw new IllegalRequestDataException("Date must be presented");
        }
    }


}
