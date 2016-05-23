package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.ReadyMeal;

import java.util.List;

public interface ReadyMealDAO {

    public List<ReadyMeal> getAllReadyMeals();

    public void addReadyMeal(ReadyMeal meal);
}
