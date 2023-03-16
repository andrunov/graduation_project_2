package ru.agorbunov.restaurant.web.menulists;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.service.MenuListService;
import ru.agorbunov.restaurant.web.testdata.AbstractControllerTest;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.menulist.AdminMenuListController.REST_URL;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.*;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_01;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.RESTAURANT_01_ID;
import static ru.agorbunov.restaurant.web.user.UserTestData.ADMIN_MAIL;
import static ru.agorbunov.restaurant.web.user.UserTestData.USER_MAIL;

public class AdminMenuListControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private MenuListService menuListService;

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

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByRestaurantAndDateForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "byRestaurantAndDate/" + RESTAURANT_01_ID)
                .param("date", "2022-12-15"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", "100006")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithName(MENU_LIST_01,  LocalDate.of(2023, Month.MARCH, 10))))
                .andDo(print())
                .andExpect(status().isNoContent());

        MenuList updated = getUpdatedMenuList();
        updated.setRestaurant(RESTAURANT_01);
        MENULIST_MATCHER.assertMatch(menuListService.get(MENU_LIST_01_ID), updated);
    }

    /*

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RESTAURANT_01_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithName(RESTAURANT_01, "Новое название")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }



    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.getExisted(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void setNewPassword() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + RESTAURANT_01_ID)
                .param("newAddress", "Новый адрес")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void setNewPasswordForbidden() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + RESTAURANT_01_ID)
                .param("newAddress", "Новый адрес")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(  null, "" );
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Restaurant invalid = getUpdatedRestaurant();
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        updated.setName("<script>name(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
     */

}
