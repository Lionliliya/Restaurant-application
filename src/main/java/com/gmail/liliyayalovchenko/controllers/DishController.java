package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.jdbc.DishDAO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DishController {

    private PlatformTransactionManager txManager;
    private DishDAO dishDAO;

    @Transactional
    public void addDish(Dish dish) {
        dishDAO.addDish(dish);
    }

    @Transactional
    public void removeDish(Dish dish) {
        dishDAO.removeDish(dish);
    }

    @Transactional
    public Dish getDishByName(String dishName) {
        return dishDAO.getDishByName(dishName);
    }

    @Transactional
    public List<Dish> getAllDishes() {
       return dishDAO.getAllDishes();
    }

    @Transactional
    public Dish getDishById(int dish_id) {
        return dishDAO.getDishById(dish_id);
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setDishDAO(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }
}
