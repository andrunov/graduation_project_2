package ru.agorbunov.restaurant.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.DishDescription;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DishDescrRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public DishDescription save(DishDescription dishDescr) {
        if (dishDescr.isNew()) {
            em.persist(dishDescr);
            return dishDescr;
        } else {
            return em.merge(dishDescr);
        }
    }

    public DishDescription get(int id) {
        return em.find(DishDescription.class, id);
    }

    @Transactional
    public boolean delete(int id) {

        try {
            DishDescription ref = em.getReference(DishDescription.class, id);
            em.remove(ref);
            return true;
        } catch (Exception e) {
            throw new NotFoundException("Restaurant with ID=" + id + " not found");
        }
    }


    public List<DishDescription> getByMenu(int menu_list_id) {
        return em.createNamedQuery(DishDescription.BY_MENU_LIST, DishDescription.class)
                .setParameter("id", menu_list_id)
                .getResultList();
    }
}
