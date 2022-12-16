package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.DishDescription;
import ru.agorbunov.restaurant.repository.DishDescriptionRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("DishDescriptionService")
public class DishDescriptionService {

    private final DishDescriptionRepository repository;


    @Autowired
    public DishDescriptionService(DishDescriptionRepository repository) {
        this.repository = repository;
    }


    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public DishDescription get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<DishDescription> getByMenu(int id) {
        return repository.getByMenu(id);
    }

    public void update(DishDescription dishDescription, int menuListId) {
        Assert.notNull(dishDescription, "dishDescription must not be null");
        repository.save(dishDescription, menuListId);
    }

    public void updateList(List<DishDescription> dishDescriptionList, int menuListId) {
        Assert.notNull(dishDescriptionList, "dishDescriptionList must not be null");
        repository.saveList(dishDescriptionList, menuListId);
    }
}
