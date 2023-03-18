package ru.agorbunov.restaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.agorbunov.restaurant.util.validation.NoHtml;


/**
 * Class represents dish
 */
@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {

    /*Name of dish*/
    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    @NoHtml
    private String name;

    public Dish() {
    }

    /** Constructor
     */
    public Dish(String name) {
        this.name = name;
    }

    public Dish(int id, String name) {
        this.id = id;
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
