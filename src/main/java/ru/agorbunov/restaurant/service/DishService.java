package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.repository.DishRepository;
import ru.agorbunov.restaurant.repository.UserRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
public class DishService {

    private final DishRepository repository;

    private final UserRepository userRepository;

    @Autowired
    public DishService(DishRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public void delete(int id, int userId) {
        userRepository.checkIsAdmin(userId);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Dish> getAll() {
        return repository.getAll();
    }


    public void update(Dish dish, int userId) {
        userRepository.checkIsAdmin(userId);
        Assert.notNull(dish, "dish must not be null");
        repository.save(dish);
    }
}
