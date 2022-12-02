package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Map;

/**
 * Class represents restaurant
 */
@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {

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
    private Map<LocalDate, MenuList> menu_lists;

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

    public Map<LocalDate, MenuList> getMenu_lists() {
        return menu_lists;
    }

    public void setMenu_lists(Map<LocalDate, MenuList> menus) {
        this.menu_lists = menus;
    }

    public void updateMenu(LocalDate date, MenuList menu) {
        this.menu_lists.put(date, menu);
    }

    public void getMenu(LocalDate date) {
        this.menu_lists.get(date);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
