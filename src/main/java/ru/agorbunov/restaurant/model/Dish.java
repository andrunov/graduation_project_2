package ru.agorbunov.restaurant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    /*price of dish*/
    @Column(name = "price", nullable = false)
    private Double price;

    public Dish() {
    }

    /** Constructor
     */
    public Dish(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", description='" + name + '\'' +
                '}';
    }
}
