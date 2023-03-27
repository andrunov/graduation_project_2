package ru.agorbunov.restaurant.web.vote;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.service.UserService;
import ru.agorbunov.restaurant.service.VoteService;
import ru.agorbunov.restaurant.util.exception.AccessDeniedException;
import ru.agorbunov.restaurant.util.exception.NotFoundException;
import ru.agorbunov.restaurant.web.AuthUser;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.agorbunov.restaurant.util.validation.ValidationUtil.assureIdConsistent;
import static ru.agorbunov.restaurant.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {

    public static final String REST_URL = "/api/user/vote";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteService voteService;

    @Autowired
    protected UserService userService;



    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote id={}", id);
        return ResponseEntity.of(Optional.of(voteService.get(id, authUser.id())));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("delete vote id={}", id);
        voteService.delete(id, authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestBody Vote vote, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create vote {}", vote);
        checkNew(vote);
        Vote created = voteService.create(vote, authUser.id());
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
        voteService.update(voteId, userId, restaurantId, menuListId, localDateTime);
    }


    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void patch(@PathVariable int id, @RequestParam LocalDateTime dateTime) {
        log.info("vote id={} update dateTime {}" , id, dateTime);
        Vote vote = voteService.getExisted(id);
        vote.setDateTime(dateTime);
    }

}
