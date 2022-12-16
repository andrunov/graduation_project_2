package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.repository.DishRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
public class DishService {

    private final DishRepository repository;


    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Dish> getAll() {
        return repository.getAll();
    }


    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        repository.save(dish);
    }
}
