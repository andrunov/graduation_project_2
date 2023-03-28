package ru.agorbunov.restaurant.web.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    public static final String REST_URL = "/api/admin/vote";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id) {
        log.info("get vote id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote id={}", id);
        service.delete(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Vote> create(@RequestParam int userId,
                                       @RequestParam int restaurantId,
                                       @RequestParam int menuListId,
                                       @RequestParam LocalDateTime localDateTime) {

        log.info("create vote userID={} and restaurantID={} and menuListID={} and localDateTime={}",
                userId, restaurantId, menuListId, localDateTime);

        Vote created = service.create(userId, restaurantId, menuListId, localDateTime);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int userId,
                       @RequestParam int voteId,
                       @RequestParam int restaurantId,
                       @RequestParam int menuListId,
                       @RequestParam LocalDateTime localDateTime) {

        log.info("update vote id={} and userID={} and restaurantID={} and menuListID={} and localDateTime={}",
                voteId, userId, restaurantId, menuListId, localDateTime);

        service.update(voteId, userId, restaurantId, menuListId, localDateTime);
    }


    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void patch(@PathVariable int id, @RequestParam LocalDateTime newDateTime) {
        log.info("vote id={} update dateTime {}" , id, newDateTime);
        service.updateDateTime(id, newDateTime);
    }



    @GetMapping("byRestaurant/{restaurantId}")
    public List<Vote> getByRestaurant(@PathVariable int restaurantId) {
        log.info("get All votes by restaurant id = {}", restaurantId);
        return service.getByRestaurant(restaurantId);
    }

    @GetMapping("byUser/{userId}")
    public List<Vote> getByUser(@PathVariable int userId) {
        log.info("get All votes by user id = {}", userId);
        return service.getByUser(userId);
    }

    @GetMapping("byRestaurantAndDate/{restaurantId}")
    public List<Vote> getByRestaurantAndDate(@PathVariable int restaurantId, @RequestParam LocalDate date) {
        log.info("get All votes by restaurant id = {} and date {}", restaurantId, date);
        return service.getByRestaurantAndDate(restaurantId, date);
    }

    @GetMapping("byUserAndDate/{userId}")
    public ResponseEntity<Vote> getByUserAndDate(@PathVariable int userId, @RequestParam LocalDate date) {
        log.info("get vote for user id={} and date={}", userId, date);
        return ResponseEntity.of(Optional.of(service.getByUserAndDate(userId, date)));
    }

    @GetMapping("byUserAndRestaurant/{userId}&{restaurantId}")
    public List<Vote> getByUserAndRestaurant(@PathVariable int userId, @PathVariable int restaurantId) {
        log.info("get All votes by user id = {} and restaurant id = {}", userId, restaurantId);
        return service.getByUserAndRestaurant(userId, restaurantId);
    }
}
