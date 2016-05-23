package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Ingredient;

import java.util.List;

public interface IngredientDAO {

    public Ingredient getIngredientById(int id);

    public List<Ingredient> getAllIngredients();
}
