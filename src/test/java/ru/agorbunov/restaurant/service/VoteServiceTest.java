package ru.agorbunov.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agorbunov.restaurant.UserTestData;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.model.Vote;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

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
        User user = userService.getWithVotes(UserTestData.USER_02_ID);
        for (Vote vote : user.getVotes().values()) {
            voteService.delete(vote.getId());
        }
        User userUpdated = userService.getWithVotes(UserTestData.USER_02_ID);
        Assert.assertEquals(userUpdated.getVotes().size(), 0);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        voteService.delete(10);
    }


}