package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.repository.DishRepository;

import java.util.List;

@Service("dishService")
public class DishService extends BaseService<DishRepository, Dish> {

    @Autowired
    public DishService(DishRepository repository) {
        super(repository);
    }

    public List<Dish> getAll() {
        return repository.getAll();
    }

    public Dish update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }
}
