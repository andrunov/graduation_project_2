package ru.agorbunov.restaurant.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.service.VoteService;
import ru.agorbunov.restaurant.util.exception.NotFoundException;
import ru.agorbunov.restaurant.web.AbstractControllerTest;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.MENU_LIST_05;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.MENU_LIST_05_ID;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_03;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_03_ID;
import static ru.agorbunov.restaurant.web.user.UserTestData.*;
import static ru.agorbunov.restaurant.web.vote.AdminVoteController.REST_URL;
import static ru.agorbunov.restaurant.web.vote.VoteTestData.*;

public class AdminVoteControllerTest  extends AbstractControllerTest {


    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteService voteService;


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_01));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE_01_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE_01_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Throwable exception = Assertions.assertThrows(NotFoundException.class, () -> {
            voteService.get(VOTE_01_ID);
        });
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE_01_ID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE_NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + VOTE_NOT_FOUND_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_01_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void setNewDateTime() throws Exception {
        LocalDateTime newDateTime = LocalDateTime.parse("2022-12-16T11:18:00");
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH  + VOTE_09_ID)
                .param("newDateTime", newDateTime.toString()))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote saved = voteService.get(VOTE_09_ID);
        VOTE_MATCHER.assertMatch(VOTE_09, saved);
        Assertions.assertEquals(newDateTime, saved.getDateTime());

    }

    @Test
    void setNewDateTimeUnathorized() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + VOTE_01_ID)
                .param("newDateTime", "2022-12-16T11:18:00"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void setNewDateTimeNotFound() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + VOTE_NOT_FOUND_ID)
                .param("newDateTime", "2022-12-16T11:18:00"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("voteId", String.valueOf(VOTE_09_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime", localDateTime.toString()))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote vote = voteService.get(VOTE_09_ID);
        vote.setDateTime(localDateTime);
        vote.setRestaurant(RESTAURANT_03);
        vote.setMenuList(MENU_LIST_05);
        Vote updated = voteService.get(VOTE_09_ID);
        VOTE_MATCHER.assertMatch(vote, updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateLate() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(12);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("voteId", String.valueOf(VOTE_09_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime", localDateTime.toString()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("voteId", String.valueOf(VOTE_09_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime", localDateTime.toString()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateForbidden_2() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("userId", String.valueOf(USER_ID))
                .param("voteId", String.valueOf(VOTE_09_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime", localDateTime.toString()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid_1() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("voteId", String.valueOf(VOTE_09_ID))
                .param("restaurantId", "12455877")
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime", localDateTime.toString()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid_2() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("voteId", String.valueOf(VOTE_09_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", "1245587")
                .param("localDateTime", localDateTime.toString()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid_3() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(9);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("userId", String.valueOf(NOT_FOUND_USER_ID))
                .param("voteId", String.valueOf(VOTE_09_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", "1245587")
                .param("localDateTime", localDateTime.toString()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime",LocalDateTime.now().toString()))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        VOTE_MATCHER.assertMatch(voteService.getExisted(newId), created);
    }


    @Test
    void createUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime",LocalDateTime.now().toString()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid_1() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("restaurantId", "45662547")
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime",LocalDateTime.now().toString()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid_2() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("userId", String.valueOf(USER_2_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", "254879954")
                .param("localDateTime",LocalDateTime.now().toString()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid_3() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("userId", String.valueOf(NOT_FOUND_USER_ID))
                .param("restaurantId", String.valueOf(RESTAURANT_03_ID))
                .param("menuListId", String.valueOf(MENU_LIST_05_ID))
                .param("localDateTime",LocalDateTime.now().toString()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
