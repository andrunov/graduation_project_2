package ru.agorbunov.restaurant.repository;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DishRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Dish save(Dish dish) {
        if (dish.isNew()) {
            em.persist(dish);
            return dish;
        } else {
            return em.merge(dish);
        }
    }

    public Dish get(int id) {
        return em.find(Dish.class, id);
    }

    @Transactional
    public boolean delete(int id) {

        try {
            Dish ref = em.getReference(Dish.class, id);
            em.remove(ref);
            return true;
        } catch (Exception e) {
            throw new NotFoundException("Restaurant with ID=" + id + " not found");
        }
    }


    public List<Dish> getAll() {
        return em.createNamedQuery(Dish.ALL_SORTED, Dish.class)
                .getResultList();
    }
}
