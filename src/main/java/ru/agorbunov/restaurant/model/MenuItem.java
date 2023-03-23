package ru.agorbunov.restaurant.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MenuItem extends BaseEntity{


    /*price of dish*/
    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(targetEntity = MenuList.class)
    @JoinColumn(name = "menu_list_id", referencedColumnName = "id")
    @JsonIgnore
    private MenuList menuList;

    @OneToOne(targetEntity = Dish.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dish dish;

    public MenuItem(MenuList menuList, Dish dish, Double price) {
        this.price = price;
        this.menuList = menuList;
        this.dish = dish;
    }

    public MenuItem(int id, MenuList menuList, Dish dish, Double price) {
        this.id = id;
        this.price = price;
        this.menuList = menuList;
        this.dish = dish;
    }

}
