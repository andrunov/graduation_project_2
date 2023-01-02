package ru.agorbunov.restaurant.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MenuItemRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public MenuItem save(MenuItem menuItem, int menuListId) {
        menuItem.setMenuList(em.getReference(MenuList.class, menuListId));
        if (menuItem.isNew()) {
            em.persist(menuItem);
            return menuItem;
        } else {
            return em.merge(menuItem);
        }
    }

    @Transactional
    public MenuItem save(MenuItem menuItem, int dishId,  int menuListId) {
        menuItem.setDish(em.getReference(Dish.class, dishId));
        menuItem.setMenuList(em.getReference(MenuList.class, menuListId));
        if (menuItem.isNew()) {
            em.persist(menuItem);
            return menuItem;
        } else {
            return em.merge(menuItem);
        }
    }

    @Transactional
    public List<MenuItem> saveList(List<MenuItem> menuItemList, int menuListId) {
        for (MenuItem menuItem : menuItemList) {
            menuItem.setMenuList(em.getReference(MenuList.class, menuListId));
            if (menuItem.isNew()) {
                em.persist(menuItem);
            } else {
                menuItem = em.merge(menuItem);
            }
        }
        return menuItemList;
    }

    public MenuItem get(int id) {
        return em.find(MenuItem.class, id);
    }

    @Transactional
    public boolean delete(int id) {

        try {
            MenuItem ref = em.getReference(MenuItem.class, id);
            em.remove(ref);
            return true;
        } catch (Exception e) {
            throw new NotFoundException("Restaurant with ID=" + id + " not found");
        }
    }


    public List<MenuItem> getByMenu(int id) {
        return em.createNamedQuery(MenuItem.BY_MENU_LIST, MenuItem.class)
                .setParameter("id", id)
                .getResultList();
    }
}
