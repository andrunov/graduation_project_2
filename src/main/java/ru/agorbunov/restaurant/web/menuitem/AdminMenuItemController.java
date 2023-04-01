package ru.agorbunov.restaurant.web.menuitem;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.service.MenuItemService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuItemController {

    public static final String REST_URL = "/api/admin/menuItems";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuItemService service;

    //todo remove if possible
    protected MenuItem prepareAndSave(MenuItem menuItem, int dishId,  int menuListId) {
        return service.update(menuItem, dishId, menuListId);
    }

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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menuItem id={}", id);
        service.delete(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MenuItem> create(@RequestParam double price,
                                           @RequestParam int dishId,
                                           @RequestParam int menuListId) {
        log.info("create menuList price {} with dish {} and menuList {} ", price, dishId, menuListId);
        MenuItem newMenuItem = new MenuItem(price);
        MenuItem created = prepareAndSave(newMenuItem, dishId, menuListId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int id,
                       @RequestParam double price,
                       @RequestParam int dishId,
                       @RequestParam int menuListId) {
        log.info("update menuList with id {} price {} with dish {} and menuList {} ",id,  price, dishId, menuListId);
        MenuItem menuItem = service.get(id);
        menuItem.setPrice(price);
        prepareAndSave(menuItem, dishId, menuListId);
    }
}
