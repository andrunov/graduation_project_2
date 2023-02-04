package ru.agorbunov.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("select v from Vote v where v.user.id=:id")
    List<Vote> getByUser(int id);

    @Query("select v from Vote v where v.restaurant.id = :id")
    List<Vote> getByRestaurant(int id);

    @Query("select v from Vote v where v.restaurant.id = :id and v.dateTime >= :from and v.dateTime < :to")
    List<Vote> getByRestaurantAndDate(int id, LocalDateTime from, LocalDateTime to);

    @Query("select v from Vote v where v.user.id = :id and v.dateTime >= :from and v.dateTime < :to")
    Vote getByUserAndDate(int id, LocalDateTime from, LocalDateTime to);

    @Query("select v from Vote v where v.user.id = :user_id and v.restaurant.id = :restaurant_id ")
    List<Vote> getByUserAndRestaurant(int userId, int restaurantId);

}
