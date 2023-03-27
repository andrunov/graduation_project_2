package ru.agorbunov.restaurant.web.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agorbunov.restaurant.service.VoteService;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    public static final String REST_URL = "/api/admin/vote";

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteService service;


}
