package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Menu;
import com.gmail.liliyayalovchenko.jdbc.MenuDAO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MenuController {

    private PlatformTransactionManager txManager;
    private MenuDAO menuDAO;

    @Transactional
    public void addNewMenu(String menuName, List<Dish> dishList) {
        menuDAO.addNewMenu(menuName, dishList);
    }

    @Transactional
    public void removeMenu(Menu menu) {
        menuDAO.removeMenu(menu);
    }

    @Transactional
    public Menu getMenuByName(String name) {
        return menuDAO.getMenuByName(name);
    }

    @Transactional
    public void showAllMenus() {
        menuDAO.showAllMenus();
    }

    @Transactional
    public void removeDishFromMenu(int menuId, Dish dish) {
        menuDAO.removeDishFromMenu(menuId, dish);
    }

    @Transactional
    public void addDishToMenu(int menuId, Dish dish) {
        menuDAO.addDishToMenu(menuId, dish);
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setMenuDAO(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }
}
