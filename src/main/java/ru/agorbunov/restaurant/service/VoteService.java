package ru.agorbunov.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.repository.UserRepository;
import ru.agorbunov.restaurant.repository.VoteRepository;
import ru.agorbunov.restaurant.util.DateTimeUtil;
import ru.agorbunov.restaurant.util.exception.AccessDeniedException;
import ru.agorbunov.restaurant.util.exception.UpdateException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service("voteService")
public class VoteService extends BaseService<VoteRepository, Vote> {

    public static final LocalTime DEADLINE = LocalTime.of(11,0);

    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository) {
        super(voteRepository);
        this.userRepository = userRepository;
    }

    /**
     * update vote for ordinal users*/
    public Vote update(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        User voteUser = userRepository.findByVote(vote.getId()).get();
        if (voteUser.id() != userId) {
            throw new AccessDeniedException("This vote does not belong to user id =" + userId);
        } else {
            LocalDate voteDate = vote.getDateTime().toLocalDate();
            if (voteDate.isBefore(LocalDate.now())) {
                throw new AccessDeniedException("Historical vote, update denied");
            } else {
                if (vote.getDateTime().isAfter(LocalDateTime.now().with(DEADLINE))) {
                    throw new AccessDeniedException("It's too late, " + DateTimeUtil.toString(vote.getDateTime()) + " vote needs to be made before 11:00 o'clock");
                } else {
                    User user = userRepository.get(userId);
                    vote.setUser(user);
                    return repository.save(vote);
                }
            }
        }
    }


    public List<Vote> getByUser(int id) {
        return repository.getByUser(id);
    }

    public List<Vote> getByRestaurant(int id) {
        return repository.getByRestaurant(id);
    }

    public List<Vote> getByRestaurantAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return repository.getByRestaurantAndDate(id, from, to);
    }

    public Vote getByUserAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return repository.getByUserAndDate(id, from, to);
    }

    public List<Vote> getByUserAndRestaurant(int userId, int restaurantId) {
        return repository.getByUserAndRestaurant(userId, restaurantId);
    }
}
