package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Class represents restaurant
 */
@NamedNativeQueries({
        @NamedNativeQuery(name = Restaurant.BY_VOTE, query = "SELECT * FROM RESTAURANTS r LEFT JOIN VOTES v on r.ID = v.RESTAURANT_ID WHERE v.ID=:id", resultClass = Restaurant.class)
})
@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r ORDER BY r.id asc ")
})

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String ALL_SORTED = "Restaurant.getAllSorted";
    public static final String BY_VOTE = "Restaurant.getByVote";

    /*name of restaurant*/
    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    private String name;

    /*address of restaurant*/
    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "address", nullable = false)
    private String address;

    /*menuLists of restaurant*/
    @OneToMany(mappedBy = "restaurant")
    private List<MenuList> menuLists;

    public Restaurant() {
    }

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MenuList> getMenuLists() {
        return menuLists;
    }

    public void setMenuLists(List<MenuList> menus) {
        this.menuLists = menus;
    }

    public void updateMenu(MenuList menu) {
        this.menuLists.add(menu);
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
