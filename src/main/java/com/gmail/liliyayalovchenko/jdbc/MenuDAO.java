package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Menu;

import java.util.List;

public interface MenuDAO {

    public void addNewMenu(String menuName, List<Dish> dishList);

    public void removeMenu(Menu menu);

    public Menu getMenuByName(String name);

    public void showAllMenus();

    public void addDishToMenu(int menuId, Dish dish);

    public void removeDishFromMenu(int menuId, Dish dish);
}
