package ru.agorbunov.restaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.agorbunov.restaurant.model.Role;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.service.UserService;
import ru.agorbunov.restaurant.util.ParsingUtil;
import ru.agorbunov.restaurant.util.ValidationUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Rest controller for user.jsp
 * to exchange user data with service-layer
 */
@RestController
@RequestMapping(value = "/ajax/admin/users")
public class UserAjaxController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    /*get user by Id */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("id") int id) {
        log.info("get " + id);
        return service.get(id);
    }

    /*get all users */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    /*delete user by Id*/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete " + id);
        service.delete(id);
    }

    /*create new user or update if exists*/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public void createOrUpdate(@RequestParam(value = "id", required = false ) Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("roles") String[] roleValues) {
        Role[] roles = ParsingUtil.parseRoles(roleValues);
        Role firstRole = roles[0];
        Role[] restRoles = Arrays.copyOfRange(roles,roles.length - 1, roleValues.length);
        User user = new User(name, email, password, firstRole, restRoles);
        user.setId(id);
        if (user.isNew()) {
            ValidationUtil.checkNew(user);
            log.info("create " + user);
            service.update(user);
        } else {
            log.info("update " + user);
            service.update(user);
        }
    }

}
