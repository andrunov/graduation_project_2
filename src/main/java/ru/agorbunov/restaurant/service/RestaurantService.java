package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.repository.MenuListRepository;
import ru.agorbunov.restaurant.repository.RestaurantRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("restaurantService")
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final MenuListRepository menuListRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MenuListRepository menuListRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuListRepository = menuListRepository;
    }


    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }



    public Restaurant getWithMenuLists(int id) {
        Restaurant restaurant = this.get(id);
        List<MenuList> votes = menuListRepository.getByRestaurant(id);
        Set<MenuList> menuListsMap = new HashSet<>(votes);
        restaurant.setMenuLists(menuListsMap);
        return restaurant;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        restaurantRepository.save(restaurant);
    }
}
