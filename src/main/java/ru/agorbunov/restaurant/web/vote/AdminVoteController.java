package ru.agorbunov.restaurant.web.vote;

import jakarta.validation.Valid;
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
import static ru.agorbunov.restaurant.util.validation.ValidationUtil.assureIdConsistent;
import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    public static final String REST_URL = "/api/admin/vote";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteService service;

    private Vote prepareAndSave(Vote vote, int userId) {
        return service.update(vote, userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id) {
        log.info("get vote id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id)));
    }

    @GetMapping("byRestaurant/{id}")
    public List<Vote> getByRestaurant(@PathVariable int restaurantId) {
        log.info("get All votes by restaurant id = {}", restaurantId);
        return service.getByRestaurant(restaurantId);
    }

    @GetMapping("byUser/{id}")
    public List<Vote> getByUser(@PathVariable int userId) {
        log.info("get All votes by user id = {}", userId);
        return service.getByUser(userId);
    }

    @GetMapping("byRestaurantAndDate/{id}")
    public List<Vote> getByRestaurantAndDate(@PathVariable int restaurantId, @RequestParam LocalDate date) {
        log.info("get All votes by restaurant id = {} and date {}", restaurantId, date);
        return service.getByRestaurantAndDate(restaurantId, date);
    }

    @GetMapping("byUserAndDate/{id}")
    public ResponseEntity<Vote> getByUserAndDate(@PathVariable int id, @RequestParam LocalDate date) {
        log.info("get vote id={}", id);
        return ResponseEntity.of(Optional.of(service.getByUserAndDate(id, date)));
    }

    @GetMapping("byUserAndRestaurant/{id}")
    public List<Vote> getByUserAndRestaurant(@PathVariable int userId, @PathVariable int restaurantId) {
        log.info("get All votes by user id = {} and restaurant id = {}", userId, restaurantId);
        return service.getByUserAndRestaurant(userId, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote id={}", id);
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote, @RequestParam int userId) {
        log.info("create vote {}", vote);
        checkNew(vote);
        Vote created = prepareAndSave(vote, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Vote vote, @PathVariable int userId) {
        log.info("update {} with userId={}", vote, userId);
        assureIdConsistent(vote, userId);
        prepareAndSave(vote, userId);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable(@PathVariable int id, @RequestParam LocalDateTime dateTime) {
        log.info("newVote vote id={}" , id);
        Vote vote = service.getExisted(id);
        vote.setDateTime(dateTime);
    }

}
