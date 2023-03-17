package ru.agorbunov.restaurant.web.dish;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.service.DishService;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = UserDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDishController {

    public static final String REST_URL = "/api/user/dish";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected DishService service;

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        log.info("get dish id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }

}
