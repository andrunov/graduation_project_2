package ru.agorbunov.restaurant.web.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.service.VoteService;
import ru.agorbunov.restaurant.web.AuthUser;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {

    public static final String REST_URL = "/api/user/vote";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote id={}", id);
        return ResponseEntity.of(Optional.of(service.get(id, authUser.id())));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("delete vote id={}", id);
        service.delete(id, authUser.id());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser authUser,
                                       @RequestParam int restaurantId,
                                       @RequestParam int menuListId,
                                       @RequestParam LocalDateTime localDateTime) {

        log.info("create vote userID={} and restaurantID={} and menuListID={} and localDateTime={}",
                authUser.id(), restaurantId, menuListId, localDateTime);

        int userId = authUser.id();
        Vote created = service.create(userId, restaurantId, menuListId, localDateTime);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser,
                       @RequestParam int voteId,
                       @RequestParam int restaurantId,
                       @RequestParam int menuListId,
                       @RequestParam LocalDateTime localDateTime) {

        log.info("update vote id={} and userID={} and restaurantID={} and menuListID={} and localDateTime={}",
                  voteId, authUser.id(), restaurantId, menuListId, localDateTime);

        int userId = authUser.id();
        service.update(voteId, userId, restaurantId, menuListId, localDateTime);
    }


    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void patch(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser, @RequestParam LocalDateTime newDateTime) {
        log.info("vote id={} update dateTime {}" , id, newDateTime);
        service.updateDateTime(id, authUser.id(), newDateTime);
    }

}
