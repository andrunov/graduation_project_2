package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

/**
 * Class represents restaurant
 */
@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r ORDER BY r.id asc "),
})
@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String ALL_SORTED = "Restaurant.getAllSorted";

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
    private Set<MenuList> menuLists;

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

    public Set<MenuList> getMenuLists() {
        return menuLists;
    }

    public void setMenuLists(Set<MenuList> menus) {
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
