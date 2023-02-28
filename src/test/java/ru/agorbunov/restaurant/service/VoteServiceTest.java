package ru.agorbunov.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.agorbunov.restaurant.service.testdata.RestaurantTestData;
import ru.agorbunov.restaurant.service.testdata.UserTestData;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.util.exception.UpdateException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void delete() throws Exception {
        User user = userService.get(UserTestData.USER_02_ID);
        user.setVotes(voteService.getByUser(UserTestData.USER_02_ID));
        for (Vote vote : user.getVotes()) {
            voteService.delete(vote.getId());
        }
        User userUpdated = userService.get(UserTestData.USER_02_ID);
        userUpdated.setVotes(voteService.getByUser(UserTestData.USER_02_ID));
        Assertions.assertEquals(4, restaurantService.getAll().size());
        Assertions.assertEquals(0, userUpdated.getVotes().size());
    }

    @Test
    public void deleteNotFound() throws Exception {
        voteService.delete(10);
        Throwable exception = Assertions.assertThrows(UpdateException.class, () -> {
            throw new UpdateException("error message");
        });
    }

    @Test
    public void getByRestaurantAndDate() throws Exception {
        List<Vote> votes = voteService.getByRestaurantAndDate(RestaurantTestData.RESTAURANT_02_ID, LocalDate.now());
        Assertions.assertEquals(2, votes.size());
    }

    @Test
    public void getByUserAndDate() throws Exception {
        Vote vote = voteService.getByUserAndDate(UserTestData.USER_04_ID, LocalDate.now());
        Assertions.assertEquals("John Bon Jovi", vote.getUser().getName());
    }

    @Test
    public void updateSuccessful() throws Exception {
        Vote vote = voteService.getByUserAndDate(UserTestData.USER_04_ID, LocalDate.now());
        LocalDateTime dateTime = LocalDateTime.now().with(LocalTime.of(11,0));
        vote.setDateTime(dateTime);
        voteService.update(vote, UserTestData.USER_04_ID);
        Vote updated = voteService.getByUserAndDate(UserTestData.USER_04_ID, LocalDate.now());
        Assertions.assertEquals(dateTime, updated.getDateTime());
    }

    @Test
    public void updateUnsuccessful() throws Exception {
        Vote vote = voteService.getByUserAndDate(UserTestData.USER_04_ID, LocalDate.now());
        LocalDateTime dateTime = LocalDateTime.now().with(LocalTime.of(11,1));
        vote.setDateTime(dateTime);
        Throwable exception = Assertions.assertThrows(UpdateException.class, () -> {
            voteService.update(vote, UserTestData.USER_04_ID);
        });
    }

    @Test
    public void updateUnsuccessful2() throws Exception {
        Vote vote = voteService.getByUserAndDate(UserTestData.USER_00_ID, LocalDate.of(2022,12,14));
        LocalDateTime dateTime = LocalDateTime.now().with(LocalTime.of(11,1));
        vote.setDateTime(dateTime);
        Throwable exception = Assertions.assertThrows(UpdateException.class, () -> {
            voteService.update(vote, UserTestData.USER_00_ID);
        });
    }

    @Test
    public void getByUserAndRestaurant() throws Exception {
        List<Vote> votes = voteService.getByUserAndRestaurant(UserTestData.USER_01_ID, RestaurantTestData.RESTAURANT_01_ID);
        Assertions.assertEquals(2, votes.size());
    }

}
