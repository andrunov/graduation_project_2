package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Class represents dish
 */
@NamedQueries({
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT d FROM Dish d ORDER BY d.id asc "),
})
@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {

    public static final String ALL_SORTED = "Dish.getAllSorted";

    /*Name of dish*/
    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    private String name;

    public Dish() {
    }

    /** Constructor
     */
    public Dish(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dish{" +
                ", name='" + name + '\'' +
                '}';
    }
}
