package ru.agorbunov.restaurant.web.menulists;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.service.MenuListService;
import ru.agorbunov.restaurant.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.agorbunov.restaurant.web.menuItems.MenuItemTestData.*;
import static ru.agorbunov.restaurant.web.menulist.AdminMenuListController.REST_URL;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.MENU_LIST_01_ID;
import static ru.agorbunov.restaurant.web.menulists.MenuListTestData.*;
import static ru.agorbunov.restaurant.web.restaurant.RestaurantTestData.*;
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
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("date", LocalDate.now().toString())
                .param("restaurantId", String.valueOf(RESTAURANT_01_ID)))
                .andExpect(status().isCreated());

        MenuList created = MENULIST_MATCHER.readFromJson(action);
        int newId = created.id();
        MenuList menuList = new MenuList();
        menuList.setDate(LocalDate.now());
        menuList.setId(newId);
        menuList.setRestaurant(RESTAURANT_01);
        MenuList saved = menuListService.getExisted(newId);
        MENULIST_MATCHER.assertMatch(saved, menuList);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("date", LocalDate.now().toString())
                .param("restaurantId",  String.valueOf(RESTAURANT_01_ID)))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("date", LocalDate.now().toString())
                .param("restaurantId",  String.valueOf(NOT_FOUND_RESTAURANT_ID)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        LocalDate date =  LocalDate.of(2023, Month.MARCH, 10);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("id", String.valueOf(MENU_LIST_01_ID))
                .param("date", date.toString())
                .param("restaurantId",  String.valueOf(RESTAURANT_01_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MenuList updated = getUpdatedMenuList();
        updated.setRestaurant(RESTAURANT_01);
        MENULIST_MATCHER.assertMatch(menuListService.get(MENU_LIST_01_ID), updated);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        LocalDate date =  LocalDate.of(2023, Month.MARCH, 10);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("id", String.valueOf(MENU_LIST_01_ID))
                .param("date", date.toString())
                .param("restaurantId",  String.valueOf(RESTAURANT_01_ID)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        LocalDate date =  LocalDate.of(2023, Month.MARCH, 10);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("id", String.valueOf(NOT_FOUND_MENU_LIST_ID))
                .param("date", date.toString())
                .param("restaurantId",  String.valueOf(RESTAURANT_01_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid_1() throws Exception {
        LocalDate date =  LocalDate.of(2023, Month.MARCH, 10);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("id", String.valueOf(MENU_LIST_01_ID))
                .param("date", date.toString())
                .param("restaurantId",  String.valueOf(NOT_FOUND_RESTAURANT_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
