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
import ru.agorbunov.restaurant.model.*;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static ru.agorbunov.restaurant.UserTestData.*;


/**
 * Created by Admin on 28.01.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB_test.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuListService menuListService;

    @Test
    public void save() throws Exception {
        User newUser = new User("Созданный пользователь",
                            "created@yandex.ru",
                            "12340Gsdf",
                            Role.ROLE_USER);
        userService.update(newUser);
        MATCHER.assertCollectionEquals(
                Arrays.asList(USER_00, USER_01, USER_02, USER_03, USER_04, USER_05, newUser),
                userService.getAll());
    }

    @Test
    public void saveWith() throws Exception {
        User newUser = new User("Созданный пользователь",
                            "created@yandex.ru",
                            "12340Gsdf",
                             Role.ROLE_USER);
        newUser.setVotes(new ArrayList<>());
        LocalDateTime now = LocalDateTime.now().with(LocalTime.of(9,0));
        Restaurant restaurant = restaurantService.get(RestaurantTestData.RESTAURANT_01_ID);
        MenuList menuList = menuListService.getByRestaurantIdAndDate(restaurant.getId(), now.toLocalDate());
        Vote vote = new Vote(now, restaurant, menuList);
        newUser.getVotes().add(vote);
        userService.update(newUser);
        for (Vote vote1: newUser.getVotes()) {
            voteService.update(vote1, newUser.getId());
        }
        MATCHER.assertCollectionEquals(
                Arrays.asList(USER_00, USER_01, USER_02, USER_03, USER_04, USER_05, newUser),
                userService.getAll());
        User updated = userService.get(newUser.getId());
        updated.setVotes(voteService.getByUser(newUser.getId()));
        Assert.assertEquals(1, updated.getVotes().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        userService.update(null);
    }

    @Test
    public void delete() throws Exception {
        userService.delete(USER_00_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_01, USER_02, USER_03, USER_04, USER_05), userService.getAll());
    }

    @Test
    public void deleteWithVotes() throws Exception {
        userService.delete(USER_02_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_00, USER_01, USER_03, USER_04, USER_05), userService.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        userService.delete(10);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(USER_00, USER_01, USER_02, USER_03, USER_04, USER_05), userService.getAll());

    }

    @Test
    public void get() throws Exception {
        User user = userService.get(USER_00_ID);
        MATCHER.assertEquals(USER_00, user);
        Assert.assertEquals(Collections.singletonList(USER_00.getRoles()),
                            Collections.singletonList(user.getRoles()));
    }

    @Test
    public void getByEmail() throws Exception {
        User user = userService.getByEmail("jbj@gmail.com");
        MATCHER.assertEquals(USER_04, user);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userService.get(10);
    }

    @Test
    public void update() throws Exception{
        User user = userService.get(USER_00_ID);
        user.setEmail("newmail@mail.ru");
        user.setName("обновленное имя");
        userService.update(user);
        MATCHER.assertEquals(user, userService.get(USER_00_ID));
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateNull() throws Exception {
        userService.update(null);
    }

    @Test
    public void getWith() throws Exception{
        User user = userService.get(USER_02_ID);
        user.setVotes(voteService.getByUser(USER_02_ID));
        Assert.assertEquals(1, user.getVotes().size());
    }


}