package ru.agorbunov.restaurant.web;


import ru.agorbunov.restaurant.MatcherFactory;
import ru.agorbunov.restaurant.model.Role;
import ru.agorbunov.restaurant.model.User;
import ru.agorbunov.restaurant.util.JsonUtil;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "meals", "password");
    public static MatcherFactory.Matcher<User> USER_WITH_MEALS_MATCHER =
            MatcherFactory.usingAssertions(User.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "meals.user", "password").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "ivanov.alexey@gmail.com";
    public static final String ADMIN_MAIL = "andrunov@gmail.com";
    public static final String GUEST_MAIL = "sidor@gmail.com";

    public static final User user = new User(100000, "Алексей Иванов", "ivanov.alexey@gmail.com", "111222",  Role.USER);
    public static final User admin = new User(100001, "Андрей Горбунов", "andrunov@gmail.com", "222333",  Role.ADMIN);
    public static final User guest = new User( 100002, "Павел Сидоров", "sidor@gmail.com", "333444",  Role.ADMIN);

    public static User getNew() {
        return new User("New", "new@gmail.com", "newPass", Role.USER);
    }

    public static User getUpdated() {
        return new User("UpdatedName", "ivanov.alexey@gmail.com", "newPass",   Role.ADMIN);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
