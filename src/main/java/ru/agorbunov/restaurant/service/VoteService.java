package ru.agorbunov.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.repository.RestaurantRepository;
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

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void delete(int id) {
        checkNotFoundWithId(voteRepository.delete(id), id);
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.get(id), id);
    }


    /**
     * update vote for ordinal users*/
    public void update(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        LocalDate voteDate = vote.getDateTime().toLocalDate();
        if (voteDate.isBefore(LocalDate.now())) {
            throw new UpdateException("Historical vote, update denied");
        } else {
            if (vote.getDateTime().isAfter(LocalDateTime.now().with(DEADLINE))) {
                throw new UpdateException("It's too late, " + DateTimeUtil.toString(vote.getDateTime()) + " vote needs to be made before 11:00 o'clock");
            } else {
                Vote saved = voteRepository.getByUserAndDate(userId, voteDate);
                if (saved != null) {
                    voteRepository.delete(saved.getId());
                }
                voteRepository.save(vote, userId);
            }
        }
    }


    /**
     * update vote for admins*/
    public void updateNoRestrictions(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        LocalDate voteDate = vote.getDateTime().toLocalDate();
        Vote saved = voteRepository.getByUserAndDate(userId, voteDate);
        if (saved != null) {
            throw new UpdateException("There is already vote for user with ID=" + userId + " and dateTime=" + DateTimeUtil.toString(vote.getDateTime()) + ", please remove it first before add new vote");
        } else {
            voteRepository.save(vote, userId);
        }
    }

    public List<Vote> getByUser(int id) {
        return voteRepository.getByUser(id);
    }

    public List<Vote> getByUserWith(int id) {
        List<Vote> result = voteRepository.getByUser(id);
        for (Vote vote : result) {
            vote.setRestaurant(restaurantRepository.getByVote(vote.getId()));
        }
        return result;
    }

    public List<Vote> getByRestaurant(int id) {
        return voteRepository.getByRestaurant(id);
    }

    public List<Vote> getByRestaurantAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return voteRepository.getByRestaurantAndDate(id, date);
    }

    public Vote getByUserAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return voteRepository.getByUserAndDate(id, date);
    }

    public List<Vote> getByUserAndRestaurant(int userId, int restaurantId) {
        return voteRepository.getByUserAndRestaurant(userId, restaurantId);
    }


}
