package ru.agorbunov.restaurant;

import ru.agorbunov.restaurant.matcher.ModelMatcher;
import ru.agorbunov.restaurant.model.Role;
import ru.agorbunov.restaurant.model.User;

/**
 * Created by Admin on 21.01.2017.
 */
public class UserTestData {

    public static final ModelMatcher<User> MATCHER = new ModelMatcher<>();


    public static final User USER_00 = new User("Алексей Иванов","ivanov.alexey@gmail.com","111222",Role.REGULAR);
    public static final User USER_01 = new User("Андрей Горбунов","andrunov@gmail.com","222333",Role.ADMIN, Role.REGULAR);
    public static final User USER_02 = new User("Павел Сидоров","sidor@gmail.com","333444", Role.REGULAR);
    public static final User USER_03 = new User("Roberto Zanetti","rzanetti@gmail.com","444555", Role.REGULAR);
    public static final User USER_04 = new User("John Bon Jovi","jbj@gmail.com","555666", Role.REGULAR);
    public static final User USER_05 = new User("Didier Maoruani","dmauruani@gmail.com","666777", Role.REGULAR);


    public static final int USER_00_ID = 100000;
    public static final int USER_01_ID = 100001;
    public static final int USER_02_ID = 100002;
    public static final int USER_04_ID = 100004;



}
