package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDAOImpl.class);

    private DataSource dataSource;
    private DishDAO dishDAO;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addNewMenu(String menuName, List<Dish> dishList) {
        LOGGER.info("Connecting to database. Method: addNewMenu(String menuName, List<Dish> dishList)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO MENU (name) values(?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, menuName);
            statement.executeUpdate();
            LOGGER.info("Menu added");
            int menu_id = getMenuByName(menuName).getId();
            for (Dish dish : dishList) {
               addDishToMenu(menu_id, dish);
            }
            LOGGER.info("All dishes added to MENU_DISH");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeMenu(Menu menu) {
        LOGGER.info("Connecting to database. Method: removeMenu(Menu menu))");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("DELETE FROM MENU WHERE id = ?")) {

            LOGGER.info("Successfully connected to DB");
            int menuId = menu.getId();
            deleteFromTableMenuDish(menuId);
            LOGGER.info("All dishes are deleted from MENU_DISH");
            statement.setInt(1, menuId);
            statement.executeUpdate();
            LOGGER.info("Menu deleted");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getMenuByName(String name) {
        Menu menu = new Menu();
        LOGGER.info("Connecting to database. Method: getMenuByName(String name)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM MENU  WHERE name = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int menuId = resultSet.getInt("id");
                menu.setId(menuId);
                menu.setName(resultSet.getString("name"));
                List<Dish> dishList = dishDAO.getDishByMenuId(menuId);
                menu.setDishList(dishList);
                LOGGER.info("Menu is got");
            }
            return menu;
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void showAllMenus() {
        LOGGER.info("Connecting to database. Method: showAllMenus()");
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {

            LOGGER.info("Successfully connected to DB");
            String sql = "SELECT * FROM MENU INNER JOIN MENU_DISH on id = menu_id";
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("id  name   menu_id   dish_id");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String menuName = resultSet.getString("name");
                int menu_id = resultSet.getInt("menu_id");
                int dish_d = resultSet.getInt("dish_id");
                System.out.println(id + "  " + menuName + "   " + menu_id + "   " + dish_d);
            }
            LOGGER.info("Menu list is printed");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDishFromMenu(int menuId, Dish dish) {
        LOGGER.info("Connecting to database. Method: removeDishFromMenu(int menuId, Dish dish))");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("DELETE FROM MENU_DISH WHERE menu_id = ? AND dish_id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, menuId);
            int dishId = dish.getId();
            statement.setInt(2, dishId);
            statement.executeUpdate();
            LOGGER.info("Dish with id " + dishId + " deleted");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDishToMenu(int menuId, Dish dish) {
        LOGGER.info("Connecting to database.  Method: addDishToMenu(int menuId, int dishId)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO MENU_DISH (menu_id, dish_id) VALUES(?, ?)")) {
            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, menuId);
            statement.setInt(2, dish.getId());
            statement.executeUpdate();
            LOGGER.info("Dish added to table Menu_Dish");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void deleteFromTableMenuDish(int menuId) {
        LOGGER.info("Connecting to database. Method: deleteFromTableMenuDish(int menuId))");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("DELETE FROM MENU_DISH WHERE menu_id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, menuId);
            statement.executeUpdate();
            LOGGER.info("Menu with id " + menuId + " deleted");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    public void setDishDAO(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
