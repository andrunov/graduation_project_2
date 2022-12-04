package ru.agorbunov.restaurant;

import ru.agorbunov.restaurant.matcher.ModelMatcher;
import ru.agorbunov.restaurant.model.Dish;
import ru.agorbunov.restaurant.model.DishDescription;

public class DishDescriptionTestData {

    public static final ModelMatcher<DishDescription> MATCHER = new ModelMatcher<>();

    public static final int MENU_LIST_01_ID = 100014;
    public static final int MENU_LIST_02_ID = 100015;

    public static final DishDescription DISH_DESCRPT_01 = new DishDescription(null, DishTestData.DISH_01,1.25);
    public static final DishDescription DISH_DESCRPT_02 = new DishDescription(null, DishTestData.DISH_02,3.45);
    public static final DishDescription DISH_DESCRPT_03 = new DishDescription(null, DishTestData.DISH_03,2.48);
    public static final DishDescription DISH_DESCRPT_04 = new DishDescription(null, DishTestData.DISH_04,5.57);
    public static final DishDescription DISH_DESCRPT_05 = new DishDescription(null, DishTestData.DISH_05,6.87);



}
