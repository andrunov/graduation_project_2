package ru.agorbunov.restaurant.web.menulists;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.web.testdata.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.menulist.AdminMenuListController.REST_URL;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.*;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_01_ID;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_02_ID;
import static ru.agorbunov.restaurant.web.user.UserTestData.ADMIN_MAIL;
import static ru.agorbunov.restaurant.web.user.UserTestData.USER_MAIL;

public class AdminMenuListControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU_LIST_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENULIST_MATCHER.contentJson(MENU_LIST_01));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByRestaurantAndDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "byRestaurantAndDate/" + RESTAURANT_01_ID)
                .param("date", "2022-12-15"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENULIST_MATCHER.contentJson(MENU_LIST_03));
    }

}
