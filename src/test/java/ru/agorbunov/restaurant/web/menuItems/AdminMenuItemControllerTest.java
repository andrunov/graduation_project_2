package ru.agorbunov.restaurant.web.menuItems;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.model.MenuItem;
import ru.agorbunov.restaurant.service.MenuItemService;
import ru.agorbunov.restaurant.util.JsonUtil;
import ru.agorbunov.restaurant.web.menuitem.AdminMenuItemController;
import ru.agorbunov.restaurant.web.testdata.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.dish.DishTestData.DISH_01;
import static ru.agorbunov.restaurant.web.menuItems.MenuItemTestData.*;
import static ru.agorbunov.restaurant.web.menuitem.AdminMenuItemController.REST_URL;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.MENU_LIST_01;
import static ru.agorbunov.restaurant.web.user.UserTestData.ADMIN_MAIL;
import static ru.agorbunov.restaurant.web.user.UserTestData.USER_MAIL;

public class AdminMenuItemControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private MenuItemService menuItemService;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU_ITEM_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(MENU_ITEM_01));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "byMenu/" + MENU_LIST_01_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(MENU_ITEM_01, MENU_ITEM_02, MENU_ITEM_03, MENU_ITEM_04, MENU_ITEM_05));
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(AdminMenuItemController.REST_URL)
                .param("menuListId", "100010")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPrice(MENU_ITEM_01, 1.48)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MenuItem updated = getUpdatedMenuItem();
        updated.setMenuList(MENU_LIST_01);
        MENU_ITEM_MATCHER.assertMatch(menuItemService.get(MENU_ITEM_01_ID), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        perform(MockMvcRequestBuilders.put(AdminMenuItemController.REST_URL)
                .param("menuListId", "100010")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPrice(MENU_ITEM_01, 1.48)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        perform(MockMvcRequestBuilders.put(AdminMenuItemController.REST_URL)
                .param("menuListId", "002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPrice(MENU_ITEM_01, 0)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(0.24);
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminMenuItemController.REST_URL)
                .param("dishId", "100022")
                .param("menuListId", "100010")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menuItem)))
                .andExpect(status().isCreated());

        MenuItem created = MENU_ITEM_MATCHER.readFromJson(action);
        int newId = created.id();
        menuItem.setId(newId);
        menuItem.setDish(DISH_01);
        menuItem.setMenuList(MENU_LIST_01);
        MENU_ITEM_MATCHER.assertMatch(menuItemService.getExisted(newId), menuItem);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(0.24);
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminMenuItemController.REST_URL)
                .param("dishId", "100022")
                .param("menuListId", "100010")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menuItem)))
                .andExpect(status().isForbidden());
    }



    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        MenuItem invalid = new MenuItem();
        invalid.setPrice(null);
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminMenuItemController.REST_URL)
                .param("dishId", "100022")
                .param("menuListId", "100010")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
