package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.repository.UserRepository;
import ru.agorbunov.restaurant.repository.VoteRepository;

import java.util.List;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFound;
import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserService {

    private final UserRepository repository;
    private final VoteRepository voteRepository;

    @Autowired
    public UserService(UserRepository repository, VoteRepository voteRepository) {
        this.repository = repository;
        this.voteRepository = voteRepository;
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public User getWithVotes(int id) {
        User user = this.get(id);
        List<Vote> votes = voteRepository.getByUser(id);
        for (Vote v : votes) {
            user.getVotes().put(v.getDateTime(), v);
        }
        return user;
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }
}
