package ru.agorbunov.restaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
