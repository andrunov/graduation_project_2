package ru.agorbunov.restaurant.web.menulist;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.service.MenuListService;

import java.time.LocalDate;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = UserMenuListController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuListController {
    public static final String REST_URL = "/api/user/menulists";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuListService service;

    @GetMapping("/{id}")
    public ResponseEntity<MenuList> get(@PathVariable int id) {
        log.info("get restaurant id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }

    @GetMapping("/byRestaurantToday/{restaurantId}")
    public ResponseEntity<MenuList> getByRestaurantToday(@PathVariable int restaurantId) {
        log.info("get restaurant id={}", restaurantId);
        return ResponseEntity.of(Optional.of(service.getByRestaurantIdAndDate(restaurantId, LocalDate.now())));
    }
}
