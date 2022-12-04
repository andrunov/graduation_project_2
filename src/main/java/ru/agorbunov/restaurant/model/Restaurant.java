package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Map;

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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "restaurant_menu",
            joinColumns = {@JoinColumn(name = "restaurant_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_list_id", referencedColumnName = "id")})
    @MapKey(name = "date")
    private Map<LocalDate, MenuList> menuLists;

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

    public Map<LocalDate, MenuList> getMenuLists() {
        return menuLists;
    }

    public void setMenuLists(Map<LocalDate, MenuList> menus) {
        this.menuLists = menus;
    }

    public void updateMenu(LocalDate date, MenuList menu) {
        this.menuLists.put(date, menu);
    }

    public void getMenu(LocalDate date) {
        this.menuLists.get(date);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
