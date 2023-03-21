package ru.agorbunov.restaurant.web.menuitem;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.service.MenuItemService;

import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = UserMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuItemController {

    public static final String REST_URL = "/api/user/menuItems";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuItemService service;

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable int id) {
        log.info("get menuItem id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }

    @GetMapping("/byMenu/{menuId}")
    public List<MenuItem> getByMenu(@PathVariable int menuId) {
        log.info("get menuItems by menulist id={}", menuId);
        return service.getByMenu(menuId);
    }
}
