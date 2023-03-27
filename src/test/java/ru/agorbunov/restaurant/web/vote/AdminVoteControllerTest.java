package ru.agorbunov.restaurant.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.service.VoteService;
import ru.agorbunov.restaurant.util.JsonUtil;
import ru.agorbunov.restaurant.web.AbstractControllerTest;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_03;
import static ru.agorbunov.restaurant.web.user.UserTestData.GUEST_MAIL;
import static ru.agorbunov.restaurant.web.user.UserTestData.USER_2_ID;
import static ru.agorbunov.restaurant.web.vote.UserVoteController.REST_URL;
import static ru.agorbunov.restaurant.web.vote.VoteTestData.VOTE_09_ID;
import static ru.agorbunov.restaurant.web.vote.VoteTestData.VOTE_MATCHER;

public class AdminVoteControllerTest  extends AbstractControllerTest {


    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteService voteService;
/*
    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void update() throws Exception {
        Vote vote = voteService.get(VOTE_09_ID);
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        vote.setDateTime(localDateTime);
        vote.setRestaurant(RESTAURANT_03);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE_09_ID + "&" + USER_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote updated = voteService.get(VOTE_09_ID);
        VOTE_MATCHER.assertMatch(vote, updated);
    }

 */
}
