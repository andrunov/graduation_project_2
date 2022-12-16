package ru.agorbunov.restaurant.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.DishDescription;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DishDescriptionRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public DishDescription save(DishDescription dishDescription, int menuListId) {
        dishDescription.setMenuList(em.getReference(MenuList.class, menuListId));
        if (dishDescription.isNew()) {
            em.persist(dishDescription);
            return dishDescription;
        } else {
            return em.merge(dishDescription);
        }
    }

    @Transactional
    public List<DishDescription> saveList(List<DishDescription> dishDescriptionList, int menuListId) {
        for (DishDescription dishDescription : dishDescriptionList) {
            dishDescription.setMenuList(em.getReference(MenuList.class, menuListId));
            if (dishDescription.isNew()) {
                em.persist(dishDescription);
            } else {
                dishDescription = em.merge(dishDescription);
            }
        }
        return dishDescriptionList;
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


    public List<DishDescription> getByMenu(int id) {
        return em.createNamedQuery(DishDescription.BY_MENU_LIST, DishDescription.class)
                .setParameter("id", id)
                .getResultList();
    }
}
