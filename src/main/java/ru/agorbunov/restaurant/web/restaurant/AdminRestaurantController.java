package ru.agorbunov.restaurant.web.restaurant;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.service.RestaurantService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.agorbunov.restaurant.util.ValidationUtil.assureIdConsistent;
import static ru.agorbunov.restaurant.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String REST_URL = "/api/admin/restaurants";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected RestaurantService service;

    protected Restaurant prepareAndSave(Restaurant restaurant) {
        return service.update(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant id={}", id);
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = prepareAndSave(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        prepareAndSave(restaurant);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable(@PathVariable int id, @RequestParam String newAddress) {
        log.info("newAddress restaurant id={}" , id);
        Restaurant restaurant = service.getExisted(id);
        restaurant.setAddress(newAddress);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }

}
