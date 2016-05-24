package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDAOImpl.class);

    private DataSource dataSource;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addNewMenu(Menu menu) {

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeMenu(Menu menu) {

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getMenuByName(String name) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Menu> getAllMenus() {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDishToMenu(Dish dish) {

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDishFromMenu(Dish dish) {

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
