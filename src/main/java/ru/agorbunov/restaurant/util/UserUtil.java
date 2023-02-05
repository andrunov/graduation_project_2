package ru.agorbunov.restaurant.util;

import ru.agorbunov.restaurant.model.Role;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.to.UserTo;

import static ru.agorbunov.restaurant.config.SecurityConfiguration.PASSWORD_ENCODER;

/**
 * class wor some common methods with User entity
 */
public class UserUtil {

    public static User createNewFromTo(UserTo newUser) {
        return new User(newUser.getName(), newUser.getEmail().toLowerCase(), newUser.getPassword(), Role.USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }


    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

}
