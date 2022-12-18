package ru.agorbunov.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agorbunov.restaurant.RestaurantTestData;
import ru.agorbunov.restaurant.UserTestData;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @Test
    public void delete() throws Exception {
        User user = userService.get(UserTestData.USER_02_ID);
        user.setVotes(voteService.getAllByUser(UserTestData.USER_02_ID));
        for (Vote vote : user.getVotes()) {
            voteService.delete(vote.getId());
        }
        User userUpdated = userService.get(UserTestData.USER_02_ID);
        userUpdated.setVotes(voteService.getAllByUser(UserTestData.USER_02_ID));
        Assert.assertEquals(userUpdated.getVotes().size(), 0);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        voteService.delete(10);
    }

    @Test
    public void getByRestaurantAndDate() throws Exception {
        List<Vote> votes = voteService.getByRestaurantAndDate(RestaurantTestData.RESTAURANT_02_ID, LocalDate.now());
        Assert.assertEquals("Roberto Zanetti", votes.get(0).getUser().getName());
    }

    @Test
    public void getByUserAndDate() throws Exception {
        List<Vote> votes = voteService.getByUserAndDate(UserTestData.USER_04_ID, LocalDate.now());
        Assert.assertEquals("John Bon Jovi", votes.get(0).getUser().getName());
    }

    @Test
    public void getByUserAndRestaurant() throws Exception {
        List<Vote> votes = voteService.getByUserAndRestaurant(UserTestData.USER_01_ID, RestaurantTestData.RESTAURANT_01_ID);
        Assert.assertEquals(1, votes.size());
    }

}
