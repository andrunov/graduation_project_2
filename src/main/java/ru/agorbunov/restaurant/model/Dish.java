package ru.agorbunov.restaurant.model;

/**
 * Class represents dish
 */
public class Dish extends BaseEntity {

    /*Name of dish*/
    private String name;

    /*price of dish*/
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
