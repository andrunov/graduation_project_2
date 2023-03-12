package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.repository.MenuListRepository;
import ru.agorbunov.restaurant.repository.RestaurantRepository;

import java.util.List;

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


    public void delete(int id) {
        restaurantRepository.deleteExisted(id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    public Restaurant getExisted(int id) {
        return restaurantRepository.getExisted(id);
    }

    public Restaurant getWithMenuLists(int id) {
        Restaurant restaurant = this.get(id);
        List<MenuList> votes = menuListRepository.getByRestaurant(id);
        restaurant.setMenuLists(votes);
        return restaurant;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public Restaurant update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public Restaurant getByVote(int id) {
        return restaurantRepository.getByVote(id);
    }
}
