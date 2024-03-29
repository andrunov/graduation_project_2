package ru.agorbunov.restaurant.web.menulist;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.service.MenuListService;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminMenuListController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuListController {

    public static final String REST_URL = "/api/admin/menulists";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuListService service;

    @GetMapping("/{id}")
    public ResponseEntity<MenuList> get(@PathVariable int id) {
        log.info("get menuList id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }

    @GetMapping("/byRestaurantAndDate/{restaurantId}")
    public ResponseEntity<MenuList> getByRestaurantAndDate(@PathVariable int restaurantId, @RequestParam LocalDate date) {
        log.info("get menuList by restaurant id={} and date {}", restaurantId, date);
        return ResponseEntity.of(Optional.of(service.getByRestaurantIdAndDate(restaurantId, date)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menuList id={}", id);
        service.delete(id);
    }

    @PostMapping()
    public ResponseEntity<MenuList> create(@RequestParam LocalDate date, @RequestParam int restaurantId) {
        log.info("create menuList with date {}, restaurantId {}", date, restaurantId);
        MenuList created = service.create(date, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int id, @RequestParam LocalDate date, @RequestParam int restaurantId) {
        log.info("create menuList with id {}, date {}, restaurantId {}", id, date, restaurantId);
        service.update(id, date, restaurantId);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void patch(@PathVariable int id, @RequestParam LocalDate newDate) {
        log.info("update menuList with id {} date {} ",id, newDate);
        service.updateDate(id, newDate);
    }
}
