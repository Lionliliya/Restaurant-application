package com.gmail.liliyayalovchenko.domains;

import java.util.List;

public class Dish {

    private int id;
    private String name;
    private String category;
    private double price;
    private int weight;
    private List<Ingredient> ingredients;

    public Dish(String name, String category, double price, int weight, List<Ingredient> ingredients) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.ingredients = ingredients;
    }

    public Dish() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", ingredients=" + ingredients +
                '}';
    }
}
