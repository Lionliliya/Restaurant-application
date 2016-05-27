package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.domains.Ingredient;
import com.gmail.liliyayalovchenko.domains.Warehouse;
import com.gmail.liliyayalovchenko.jdbc.IngredientDAO;
import com.gmail.liliyayalovchenko.jdbc.WarehouseDAO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class WarehouseController {
    private PlatformTransactionManager txManager;
    private WarehouseDAO warehouseDAO;
    private IngredientDAO ingredientDAO;

    @Transactional
    public List<Warehouse> getAllWarehouseIngred(){
        List<Warehouse> ingredientList = warehouseDAO.getAllIngredients();
        return ingredientList;
    }

    @Transactional
    public void addIngredient(Ingredient ingredient, int amount){
       if (!ingredientDAO.exist(ingredient)) {
           ingredientDAO.addIngredient(ingredient);
           System.out.println("Ingredient was added to Ingredients. Now you can put int to warehouse");
       } else if (!warehouseDAO.alreadyExist(ingredient)) {
           warehouseDAO.addIngredient(ingredient, amount);
       } else {
           System.out.println("Ingredient already exist");
       }
    }

    @Transactional
    public void removeIngredient(String ingredient){
        warehouseDAO.removeIngredient(ingredient);
    }

    @Transactional
    public void changeAmount(Ingredient ingredient, int delta, boolean increase) {
        warehouseDAO.changeAmount(ingredient, delta, increase);
    }

    @Transactional
    public Warehouse findByName(String ingredientName) {
        return warehouseDAO.findByName(ingredientName);
    }

    @Transactional
    public List<Warehouse> getLuckIngredients() {
        List<Warehouse> ingredientList = warehouseDAO.getEndingIngredients();
        return ingredientList;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
        this.warehouseDAO = warehouseDAO;
    }

    public void setIngredientDAO(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }
}
