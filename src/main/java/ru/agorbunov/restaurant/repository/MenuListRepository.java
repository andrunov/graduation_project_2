package ru.agorbunov.restaurant.repository;


import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MenuListRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public MenuList save(MenuList menuList, int restaurantId) {
        menuList.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        if (menuList.isNew()) {
            em.persist(menuList);
            return menuList;
        } else {
            return em.merge(menuList);
        }
    }

    public MenuList get(int id) {
        return em.find(MenuList.class, id);
    }

    @Transactional
    public boolean delete(int id) {

        try {
            MenuList ref = em.getReference(MenuList.class, id);
            em.remove(ref);
            return true;
        } catch (Exception e) {
            throw new NotFoundException("Restaurant with ID=" + id + " not found");
        }
    }


    public List<MenuList> getByRestaurant(int id) {
        return em.createNamedQuery(MenuList.BY_RESTAURANT, MenuList.class)
                .setParameter("id", id)
                .getResultList();
    }


    public MenuList getByRestaurantIdAndDate(int id, LocalDate date) {
        List<MenuList> menuLists = em.createNamedQuery(MenuList.BY_RESTAURANT_AND_DATE, MenuList.class)
                .setParameter("id", id)
                .setParameter("date", date)
                .getResultList();

        return DataAccessUtils.singleResult(menuLists);
    }
}
