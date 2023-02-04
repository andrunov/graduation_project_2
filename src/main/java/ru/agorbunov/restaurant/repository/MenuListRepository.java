package ru.agorbunov.restaurant.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.MenuList;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuListRepository extends BaseRepository<MenuList> {

    @Query("select ml from MenuList ml where ml.restaurant.id=:id")
    List<MenuList> getByRestaurant(int id);


    @Query("select ml from MenuList ml where ml.restaurant.id=:id and ml.date=:date")
    MenuList getByRestaurantIdAndDate(int id, LocalDate date);
}
