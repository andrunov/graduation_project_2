package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNotFound;

@Service
public class UserService extends BaseService<UserRepository, User> {

    @Autowired
    public UserService(UserRepository repository) {
       super(repository);
    }

    public Optional<User> getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.findByEmailIgnoreCase(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }

}
