package ru.agorbunov.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.repository.VoteRepository;

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
}
