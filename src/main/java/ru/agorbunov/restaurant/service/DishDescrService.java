package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.DishDescription;
import ru.agorbunov.restaurant.repository.DishDescriptionRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("dishDescrService")
public class DishDescrService {

    private final DishDescriptionRepository repository;

    @Autowired
    public DishDescrService(DishDescriptionRepository repository) {
        this.repository = repository;
    }


    public DishDescription create(DishDescription dishDescr) {
        Assert.notNull(dishDescr, "dishDescription must not be null");
        return repository.save(dishDescr);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public DishDescription get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<DishDescription> getByMenu(int menu_list_id) {
        return repository.getByMenu(menu_list_id);
    }

    public void update(DishDescription dishDescr) {
        Assert.notNull(dishDescr, "dishDescr must not be null");
        repository.save(dishDescr);
    }
}
