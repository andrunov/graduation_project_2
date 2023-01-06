package ru.agorbunov.restaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.service.VoteService;
import ru.agorbunov.restaurant.util.DateTimeUtil;
import ru.agorbunov.restaurant.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/ajax/votes")
public class VoteAjaxController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    @Autowired
    private CurrentEntities currentEntities;

    /*get all votes by current user and dateTime*/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByUserAndDate(@RequestParam(value = "dateKey",required = false) String date) {
        log.info("getByUserAndDate");
        int userId = currentEntities.getCurrentUser().getId();
        List<Vote> result = null;
        if (ValidationUtil.checkEmpty(date)){
            LocalDate dateTime = DateTimeUtil.parseLocalDate(date);
            Vote vote = voteService.getByUserAndDate(userId, dateTime);
            if (vote != null) {
                result = new ArrayList<>();
                result.add(voteService.getByUserAndDate(userId, dateTime));
            }
        }else {
            result = voteService.getByUser(userId);
        }
        return result;
    }

    /*get menuList by Id */
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote getVote(@PathVariable("id") int id) {
        log.info("get " + id);
        return voteService.get(id);
    }


    /*delete menuList by Id*/
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete " + id);
        voteService.delete(id);
    }

    /*create new menuList or update if exist*/
    @PostMapping
    public void createOrUpdate(@RequestParam(value = "id", required = false) Integer id){
        Vote vote = new Vote();
        int userId = currentEntities.getCurrentUser().getId();
        vote.setId(id);
        vote.setRestaurant(currentEntities.getCurrentRestaurant());
        vote.setDateTime(LocalDateTime.now());
        checkEmpty(vote);
        if (vote.isNew()) {
            ValidationUtil.checkNew(vote);
            log.info("create " + vote);
            voteService.update(vote, userId);
        } else {
            log.info("update " + vote);
            voteService.update(vote, userId);
        }
    }

    /*check menuList for empty fields*/
    private void checkEmpty(Vote vote){
        ValidationUtil.checkEmpty(vote.getDateTime(),"dateTime");
    }


}
