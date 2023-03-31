package ru.agorbunov.restaurant.web.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.service.UserService;
import ru.agorbunov.restaurant.util.UserUtil;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractUserController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected UserService service;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        log.info("get user id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }

    public void delete(int id) {
        log.info("delete user id={}", id);
        service.delete(id);
    }

    protected User prepareAndSave(User user) {
        return service.update(UserUtil.prepareToSave(user));
    }

    protected ResponseEntity<User> getWithVotes(int id) {
        return ResponseEntity.of(service.getWithVotes(id));
    }
}