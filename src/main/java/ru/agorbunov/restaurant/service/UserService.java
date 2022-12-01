package ru.agorbunov.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.repository.UserRepositoryImpl;

import java.util.List;

@Service("userService")
public class UserService {

    private final UserRepositoryImpl repository;

    @Autowired
    public UserService(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public User get(int id) {
        return repository.get(id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return repository.getByEmail(email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }
}
