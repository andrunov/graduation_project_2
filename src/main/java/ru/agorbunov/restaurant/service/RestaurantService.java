package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.repository.MenuListRepository;
import ru.agorbunov.restaurant.repository.RestaurantRepository;

import java.util.List;

@Service("restaurantService")
public class RestaurantService extends BaseService<RestaurantRepository, Restaurant> {

    private final MenuListRepository menuListRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MenuListRepository menuListRepository) {
        super(restaurantRepository);
        this.menuListRepository = menuListRepository;
    }

    public Restaurant getWithMenuLists(int id) {
        Restaurant restaurant = this.get(id);
        List<MenuList> votes = menuListRepository.getByRestaurant(id);
        restaurant.setMenuLists(votes);
        return restaurant;
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public Restaurant update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public Restaurant getByVote(int id) {
        return repository.getByVote(id);
    }
}
