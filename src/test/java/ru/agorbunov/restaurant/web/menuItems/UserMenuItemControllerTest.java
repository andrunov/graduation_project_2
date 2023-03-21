package ru.agorbunov.restaurant.web.menuItems;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.web.testdata.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.menuItems.MenuItemTestData.*;
import static ru.agorbunov.restaurant.web.menuitem.UserMenuItemController.REST_URL;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.MENU_LIST_01;
import static ru.agorbunov.restaurant.web.user.UserTestData.USER_MAIL;

public class UserMenuItemControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';


    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU_ITEM_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(MENU_ITEM_01));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "byMenu/" + MENU_LIST_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(MENU_ITEM_01, MENU_ITEM_02, MENU_ITEM_03, MENU_ITEM_04, MENU_ITEM_05));
    }
}
