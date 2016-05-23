package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;

import java.util.List;

public interface DishDAO {

    public void addDish(Dish dish);

    public void removeDish(Dish dish);

    public Dish getDishByName(String dishName);

    public List<Dish> getAllDishes();
}
