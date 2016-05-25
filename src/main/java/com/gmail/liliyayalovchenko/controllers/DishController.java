package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.jdbc.DishDAO;
import org.springframework.transaction.PlatformTransactionManager;

public class DishController {

    private PlatformTransactionManager txManager;
    private DishDAO dishDAO;

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setDishDAO(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }
}
