package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.domains.ReadyMeal;
import com.gmail.liliyayalovchenko.jdbc.ReadyMealDAO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ReadyMealController {

    private PlatformTransactionManager txManager;
    private ReadyMealDAO readyMealDAO;

    @Transactional
    public List<ReadyMeal> getAllMeal() {
        return readyMealDAO.getAllReadyMeals();
    }

    @Transactional
    public void addMeal(ReadyMeal meal) {
        readyMealDAO.addReadyMeal(meal);
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setReadyMealDAO(ReadyMealDAO readyMealDAO) {
        this.readyMealDAO = readyMealDAO;
    }
}
