package ru.agorbunov.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.repository.VoteRepository;
import ru.agorbunov.restaurant.util.DateTimeUtil;
import ru.agorbunov.restaurant.util.exception.UpdateException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("voteService")
public class VoteService {

    public static final LocalTime DEADLINE = LocalTime.of(11,0);

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Vote get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }


    public void update(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        LocalDate voteDate = vote.getDateTime().toLocalDate();
        if (voteDate.isBefore(LocalDate.now())) {
            throw new UpdateException("Historical vote, update denied");
        } else {
            if (vote.getDateTime().isAfter(LocalDateTime.now().with(DEADLINE))) {
                throw new UpdateException("It's too late, " + DateTimeUtil.toString(vote.getDateTime()) + " vote needs to be made before 11:00 o'clock");
            } else {
                repository.save(vote, userId);
            }
        }
    }

    public List<Vote> getAllByUser(int id) {
        return repository.getAllByUser(id);
    }

    public List<Vote> getByRestaurantAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return repository.getByRestaurantAndDate(id, date);
    }

    public Vote getByUserAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return repository.getByUserAndDate(id, date);
    }

    public List<Vote> getByUserAndRestaurant(int userId, int restaurantId) {
        return repository.getByUserAndRestaurant(userId, restaurantId);
    }


}
