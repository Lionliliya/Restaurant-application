package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.domains.Ingredient;
import com.gmail.liliyayalovchenko.domains.Warehouse;
import com.gmail.liliyayalovchenko.jdbc.WarehouseDAO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class WarehouseController {
    private PlatformTransactionManager txManager;
    private WarehouseDAO warehouseDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Warehouse> getAllWarehouseIngred(){
        List<Warehouse> ingredientList = warehouseDAO.getAllIngredients();
        return ingredientList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addIngredient(Ingredient ingredient, int amount){
       warehouseDAO.addIngredient(ingredient, amount);
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
        this.warehouseDAO = warehouseDAO;
    }
}
