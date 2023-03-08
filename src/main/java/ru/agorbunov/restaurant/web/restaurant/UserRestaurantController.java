package ru.agorbunov.restaurant.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.service.RestaurantService;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    public static final String REST_URL = "/api/user/restaurants";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected RestaurantService service;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }

    @GetMapping("/{id}/with-menu")
    public ResponseEntity<Restaurant> getWithMenuLists(@PathVariable int id)  {
        log.info("getWithMenuLists restaurant id={}", id);
        return ResponseEntity.of(Optional.of(service.getWithMenuLists(id)));
    }
}
