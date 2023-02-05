package ru.agorbunov.restaurant.web.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.repository.UserRepository;
import ru.agorbunov.restaurant.util.UserUtil;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractUserController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected UserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    public ResponseEntity<User> getWithMeals(int id) {
        log.info("getWithMeals {}", id);
        return ResponseEntity.of(repository.getWithMeals(id));
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}