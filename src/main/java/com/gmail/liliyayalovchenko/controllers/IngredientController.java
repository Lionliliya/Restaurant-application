package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.domains.Ingredient;
import com.gmail.liliyayalovchenko.jdbc.IngredientDAO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class IngredientController {

    private PlatformTransactionManager txManager;
    private IngredientDAO ingredientDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredientList = ingredientDAO.getAllIngredients();
        return ingredientList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Ingredient getIngredientById(int id){
        Ingredient ingredient = ingredientDAO.getIngredientById(id);
        return ingredient;
    }

    @Transactional
    public Ingredient getIngredientByName(String name) {
        Ingredient ingredient = ingredientDAO.getIngredientByName(name);
        return ingredient;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setIngredientDAO(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }
}
