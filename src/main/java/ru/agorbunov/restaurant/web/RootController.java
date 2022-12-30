package ru.agorbunov.restaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import ru.agorbunov.restaurant.model.MenuList;
import ru.agorbunov.restaurant.model.Restaurant;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.service.*;
import ru.agorbunov.restaurant.to.MenuListTo;
import ru.agorbunov.restaurant.to.UserTo;
import ru.agorbunov.restaurant.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Root-controller. Map root requests
 */
@Controller
public class RootController {

    /*logger*/
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private MenuListService menuListService;

    @Autowired
    private DishService dishService;

    /*return admin_home.jsp and display home page*/
    @GetMapping(value = "/")
    public String root() {
        log.info("get /");
        return "redirect:/home";
    }

    /*if request have errors put them into model and return back to login.jsp*/
    @GetMapping(value = "/login")
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {
        log.info("get/login");
        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    /*return home page according to userTo role*/
    @GetMapping(value = "/home")
    public String welcome(HttpServletRequest request, Model model){
        log.info("get/home");
        CurrentEntities.setCurrentUser(AuthorizedUser.get().getLoggedUser());
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "admin_home";
        }else {
            model.addAttribute(CurrentEntities.getCurrentUser());
            return "user_home";
        }
    }

    @GetMapping("/profile")
    public String profile() {
        log.info("get /profile");
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(ModelMap model, @Valid UserTo userTo, BindingResult result, SessionStatus status) {
        log.info("post /profile");
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()){
                model.addAttribute(fieldError.getField()+"ErrorMessage",fieldError.getDefaultMessage());
            }
            return "profile";
        } else {
            User user = UserUtil.updateFromTo(AuthorizedUser.get().getLoggedUser(),userTo);
            userService.update(user);
            AuthorizedUser.get().setLoggedUser(user);
            status.setComplete();
            return "redirect:home";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        log.info("get /register");
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        log.info("post /register");
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            for (FieldError fieldError : result.getFieldErrors()){
                model.addAttribute(fieldError.getField()+"ErrorMessage",fieldError.getDefaultMessage());
            }
            return "profile";
        } else {
            User user = UserUtil.createNewFromTo(userTo);
            userService.update(user);
            status.setComplete();
            return "redirect:login?message=registersuccessfull&username=" + userTo.getEmail();
        }
    }

    /*return users.jsp and display all users*/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        log.info("get /users");
        return "users";
    }

    /*return restaurants.jsp and display all restaurants*/
    @GetMapping(value = "/restaurants")
    public String restaurants() {
        log.info("get /restaurants");
        return "restaurants";
    }

    /*get id parameter to set current restaurant and redirect to menuLists.jsp*/
    @GetMapping(value = "/menuLists/{id}")
    public String menuLists(@PathVariable("id") int id) {
        log.info("get /menuLists/{id}");
        CurrentEntities.setCurrentRestaurant(restaurantService.get(id));
        return "redirect:/menuLists";
    }

    /*return menuLists.jsp and display menu lists of current restaurant*/
    @GetMapping(value = "/menuLists")
    public String menuLists(Model model) {
        log.info("get /menuLists");
        model.addAttribute(CurrentEntities.getCurrentRestaurant());
        return "menuLists";
    }

    /*get id parameter to set current restaurant and redirect to menuLists.jsp*/
    @GetMapping(value = "/menuItems/{id}")
    public String menuItems(@PathVariable("id") int id) {
        log.info("get /menuItems/{id}");
        CurrentEntities.setCurrentMenuListTo(MenuListTo.fromMenuList(menuListService.get(id)));
        return "redirect:/menuItems";
    }

    /*return menuLists.jsp and display menu lists of current restaurant*/
    @GetMapping(value = "/menuItems")
    public String menuItems(Model model) {
        log.info("get /menuItems");
        model.addAttribute(CurrentEntities.getCurrentRestaurant());
        model.addAttribute(CurrentEntities.getCurrentMenuListTo());
        return "menuItems";
    }






    /*get id parameter to set current userTo and redirect to orders.jsp*/
    @GetMapping(value = "/orders/{id}")
    public String orders(@PathVariable("id") int id) {
        log.info("get /orders/{id}");
        CurrentEntities.setCurrentUser(userService.get(id));
        return "redirect:/orders";
    }

    /*return orders.jsp and display orders of current userTo*/
    @GetMapping(value = "/orders")
    public String orders(Model model) {
        log.info("get /orders");
        model.addAttribute("currentUser",CurrentEntities.getCurrentUser());
        return "orders";
    }

    /*get id parameter to set current menuList and redirect to dishes.jsp*/
    @GetMapping(value = "/dishes/{id}")
    public String dishes(@PathVariable("id") int id) {
        log.info("get /dishes/{id}");
        Restaurant restaurant = CurrentEntities.getCurrentRestaurant();
     //   CurrentEntities.setCurrentMenuListTo(menuListService.get(id));
        return "redirect:/dishes";
    }

    /*return dishes.jsp and display dishes of current menu list of current restaurant*/
    @GetMapping(value = "/dishes")
    public String dishes(Model model) {
        log.info("get /dishes");
        model.addAttribute("restaurant",CurrentEntities.getCurrentRestaurant());
        //   model.addAttribute("description",CurrentEntities.getCurrentMenuList().getDescription());
        //   model.addAttribute("localDate",CurrentEntities.getCurrentMenuList().getDateTime().toLocalDate().toString());
        return "dishes";
    }


    /*get id parameter to set current dish and redirect to orders_by_dish.jsp*/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/orders_by_dish/{id}")
    public String ordersByDish(@PathVariable("id") int id){
        log.info("get /orders_by_dish/{id}");
        //MenuList menuList = CurrentEntities.getCurrentMenuListTo();
        CurrentEntities.setCurrentDish(dishService.get(id));
        return "redirect:/orders_by_dish";
    }



}
