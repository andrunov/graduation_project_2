package ru.agorbunov.restaurant.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.service.VoteService;
import ru.agorbunov.restaurant.util.JsonUtil;
import ru.agorbunov.restaurant.util.exception.NotFoundException;
import ru.agorbunov.restaurant.web.AbstractControllerTest;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_03;
import static ru.agorbunov.restaurant.web.user.UserTestData.*;
import static ru.agorbunov.restaurant.web.vote.UserVoteController.REST_URL;
import static ru.agorbunov.restaurant.web.vote.VoteTestData.*;

public class UserVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteService voteService;


    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_01));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE_01_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE_01_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            voteService.get(VOTE_01_ID);
        });
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE_01_ID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_01_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void setNewDate() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH  + VOTE_01_ID)
                .param("dateTime", "2022-12-16T11:18:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    void setNewDateUnathorized() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + VOTE_01_ID)
                .param("dateTime", "2022-12-16T11:18:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void setNewDateInvalid() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + NOT_FOUND_ID)
                .param("dateTime", "2022-12-16T11:18:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void update() throws Exception {
        Vote vote = voteService.get(VOTE_09_ID);
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        vote.setDateTime(localDateTime);
        vote.setRestaurant(RESTAURANT_03);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE_09_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote updated = voteService.get(VOTE_09_ID);
        VOTE_MATCHER.assertMatch(vote, updated);
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void updateLate() throws Exception {
        Vote vote = voteService.get(VOTE_09_ID);
        LocalDateTime localDateTime = LocalDateTime.now().withHour(12);
        vote.setDateTime(localDateTime);
        vote.setRestaurant(RESTAURANT_03);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE_09_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        Vote vote = voteService.get(VOTE_09_ID);
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        vote.setDateTime(localDateTime);
        vote.setRestaurant(RESTAURANT_03);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE_09_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Vote vote = voteService.get(VOTE_09_ID);
        vote.setDateTime(null);
        vote.setRestaurant(RESTAURANT_03);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE_09_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }



    /*


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.getExisted(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isForbidden());
    }




    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(  null, "" );
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


     */
}
