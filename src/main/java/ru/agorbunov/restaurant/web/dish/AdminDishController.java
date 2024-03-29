package ru.agorbunov.restaurant.web.dish;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.service.DishService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    public static final String REST_URL = "/api/admin/dish";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected DishService service;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish id={}", id);
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish) {
        log.info("create dish {}", dish);
        checkNew(dish);
        Dish created = service.update(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish) {
        log.info("update dish {}", dish);
        service.update(dish);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable(@PathVariable int id, @RequestParam String newName) {
        log.info("newAddress dish id={}" , id);
        Dish dish = service.getExisted(id);
        dish.setName(newName);
    }

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll dishes");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        log.info("get dish id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }
}
