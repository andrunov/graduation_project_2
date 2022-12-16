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

    private final DishDescriptionRepository dishDescriptionRepository;


    @Autowired
    public DishDescriptionService(DishDescriptionRepository dishDescriptionRepository) {
        this.dishDescriptionRepository = dishDescriptionRepository;
    }


    public void delete(int id) {
        checkNotFoundWithId(dishDescriptionRepository.delete(id), id);
    }

    public DishDescription get(int id) {
        return checkNotFoundWithId(dishDescriptionRepository.get(id), id);
    }

    public List<DishDescription> getByMenu(int id) {
        return dishDescriptionRepository.getByMenu(id);
    }

    public void update(DishDescription dishDescription, int menuListId) {
        Assert.notNull(dishDescription, "dishDescription must not be null");
        dishDescriptionRepository.save(dishDescription, menuListId);
    }

    public void updateList(List<DishDescription> dishDescriptionList, int menuListId) {
        Assert.notNull(dishDescriptionList, "dishDescriptionList must not be null");
        dishDescriptionRepository.saveList(dishDescriptionList, menuListId);
    }
}
