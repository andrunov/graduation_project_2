package ru.agorbunov.restaurant.web.menulists;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.menulist.UserMenuListController.REST_URL;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.*;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_02_ID;
import static ru.agorbunov.restaurant.web.user.UserTestData.USER_MAIL;

public class UserMenuListControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU_LIST_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENULIST_MATCHER.contentJson(MENU_LIST_01));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByRestaurantToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "byRestaurantToday/" + RESTAURANT_02_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENULIST_MATCHER.contentJson(MENU_LIST_05));
    }

}
