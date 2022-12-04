package ru.agorbunov.restaurant.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = DishDescription.BY_MENU_LIST, query = "SELECT dd from DishDescription dd where dd.menuList.id=:id")
})
@Entity
@Table(name = "dish_descriptions")
public class DishDescription extends BaseEntity{


    public static final String BY_MENU_LIST = "DishDescription.byMenuList";

    /*price of dish*/
    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(targetEntity = MenuList.class)
    @JoinColumn(name = "menu_list_id", referencedColumnName = "id")
    private MenuList menuList;

    @OneToOne(targetEntity = Dish.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public DishDescription() {
    }

    public DishDescription(MenuList menuList, Dish dish, Double price) {
        this.price = price;
        this.menuList = menuList;
        this.dish = dish;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public MenuList getMenuList() {
        return menuList;
    }

    public void setMenuList(MenuList menuList) {
        this.menuList = menuList;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
