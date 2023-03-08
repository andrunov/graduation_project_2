package ru.agorbunov.restaurant.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.service.RestaurantService;
import ru.agorbunov.restaurant.web.testdata.AbstractControllerTest;
import ru.agorbunov.restaurant.web.user.AdminUserController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.restaurant.AdminRestaurantController.REST_URL;
import static ru.agorbunov.restaurant.web.testdata.RestaurantTestData.*;
import static ru.agorbunov.restaurant.web.testdata.UserTestData.*;
import static ru.agorbunov.restaurant.web.testdata.UserTestData.guest_4;

public class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private RestaurantService restaurantService;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminRestaurantController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_01, RESTAURANT_02, RESTAURANT_03, RESTAURANT_04));
    }


}
