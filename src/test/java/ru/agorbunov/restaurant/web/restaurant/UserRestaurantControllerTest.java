package ru.agorbunov.restaurant.web.restaurant;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.web.testdata.AbstractControllerTest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.*;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.*;
import static ru.agorbunov.restaurant.web.restaurant.UserRestaurantController.REST_URL;
import static ru.agorbunov.restaurant.web.user.UserTestData.USER_MAIL;

public class UserRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_01));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMenuLists() throws Exception {
        List<MenuList> menuList = new ArrayList<>();
        menuList.add(MENU_LIST_01);
        menuList.add(MENU_LIST_02);
        menuList.add(MENU_LIST_03);
        RESTAURANT_01.setMenuLists(menuList);
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_01_ID + "/with-menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_MATCHER.contentJson(RESTAURANT_01));
    }
}
