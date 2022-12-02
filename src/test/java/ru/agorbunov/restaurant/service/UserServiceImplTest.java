package ru.agorbunov.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static ru.agorbunov.restaurant.UserTestData.*;


/**
 * Created by Admin on 28.01.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceImplTest {


    @Autowired
    private UserService service;


    @Test
    public void save() throws Exception {
        service.update(USER_CREATED);
        MATCHER.assertCollectionEquals(
                Arrays.asList(USER_01, USER_02, USER_03, USER_04, USER_05, USER_06, USER_CREATED),
                service.getAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        service.update(null);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_01_ID);
        MATCHER.assertCollectionEquals(Arrays.asList( USER_02, USER_03, USER_04, USER_05, USER_06), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(10);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(USER_01, USER_02, USER_03, USER_04, USER_05, USER_06), service.getAll());

    }

    @Test
    public void get() throws Exception {
        User user = service.get(USER_01_ID);
        MATCHER.assertEquals(USER_01, user);
        Assert.assertEquals(Collections.singletonList(USER_01.getRoles()),
                            Collections.singletonList(user.getRoles()));
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("jbj@gmail.com");
        MATCHER.assertEquals(USER_05, user);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(10);
    }

    @Test
    public void update() throws Exception{
        User user = service.get(USER_01_ID);
        user.setEmail("newmail@mail.ru");
        user.setName("обновленное имя");
        user.setVotes(new HashMap<>());
        service.update(user);
        MATCHER.assertEquals(user,service.get(USER_01_ID));
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateNull() throws Exception {
        service.update(null);
    }

    @Test
    public void getWith() throws Exception{
        /*
        ModelMatcher<Order> OrderMatcher = new ModelMatcher<>();
        User user = service.getWithOrders(USER_02_ID);
        OrderMatcher.assertCollectionEquals(USER_02.getOrders(),user.getOrders());

         */
    }


}