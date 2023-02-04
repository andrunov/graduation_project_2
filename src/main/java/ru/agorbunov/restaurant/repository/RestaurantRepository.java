package ru.agorbunov.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.id asc ")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r LEFT JOIN Vote v on r.id = v.restaurant.id WHERE v.id=:id")
    Restaurant getByVote(int id);
}
