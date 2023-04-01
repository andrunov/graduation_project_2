package ru.agorbunov.restaurant.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.MenuItem;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT mi from MenuItem mi where mi.menuList.id=:id")
    List<MenuItem> getByMenu(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE MenuItem mi SET mi.price=:newPrice WHERE mi.id=:id")
    void updateDateTime (int id, double newPrice);
}
