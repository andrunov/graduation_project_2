package ru.agorbunov.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("voteService")
public class VoteService {

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


    public void update(Vote vote, User user) {
        Assert.isTrue(!user.isNew(), "user must not be new");
        Assert.notNull(vote, "vote must not be null");
        repository.save(vote, user.getId());
    }

    public List<Vote> getAllByUser(int id) {
        return repository.getAllByUser(id);
    }

    public List<Vote> getByRestaurantAndDate(int id, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return repository.getByRestaurantAndDate(id, date);
    }
}
