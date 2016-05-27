package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Ingredient;
import com.gmail.liliyayalovchenko.domains.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAOImpl implements WarehouseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseDAOImpl.class);
    private DataSource dataSource;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addIngredient(Ingredient ingredient, int amount) {
        LOGGER.info("Connecting to database. Method: addIngredient(Ingredient ingredient, int amount)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO WAREHOUSE (ingred_id, amount) values(?, ?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, ingredient.getId());
            statement.setInt(2, amount);
            statement.executeUpdate();
            LOGGER.info("Ingredient added");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeIngredient(String ingredientName) {
        LOGGER.info("Connecting to database. Method: removeIngredient(String ingredientName)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM WAREHOUSE WHERE ingred_id = (SELECT id FROM INGREDIENT WHERE name = ?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, ingredientName);
            statement.executeUpdate();
            LOGGER.info("Ingredient deleted");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeAmount(Ingredient ingredient, int delta, boolean increase) {
        LOGGER.info("Connecting to database. Method: changeAmount(Ingredient ingredient, int delta, boolean increase)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = increase ? connection.prepareStatement
                    ("UPDATE WAREHOUSE SET amount=amount+? WHERE ingred_id = ?") : connection.prepareStatement
                    ("UPDATE WAREHOUSE SET amount=amount-? WHERE ingred_id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, delta);
            statement.setInt(2, ingredient.getId());
            statement.executeUpdate();
            LOGGER.info("Amount of ingredient updated");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Warehouse findByName(String ingredientName) {
        LOGGER.info("Connecting to database. Method:  findByName(String ingredientName)");
        Warehouse ingredient;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM WAREHOUSE WHERE ingred_id = " +
                            "(SELECT id FROM INGREDIENT WHERE name = ?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, ingredientName);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("ResultSet is got");
            if(resultSet.next()) {
                ingredient = createWarehouseIngred(resultSet);
            }  else {
                LOGGER.error("Cannot find ingredient by this name");
                throw new RuntimeException("Cannot find ingredient by this name");
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Warehouse> getAllIngredients() {
        List<Warehouse> ingredientList = new ArrayList<>();
        LOGGER.info("Connecting to database. Method: getAllIngredients()");
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {

            LOGGER.info("Successfully connected to DB");
            String sql = "SELECT * FROM WAREHOUSE";
            ResultSet resultSet = statement.executeQuery(sql);
            LOGGER.info("Result set is got");
            while (resultSet.next()) {
                Warehouse ingredient = createWarehouseIngred(resultSet);
                ingredientList.add(ingredient);
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }

        return ingredientList;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Warehouse> getEndingIngredients() {
        List<Warehouse> ingredientList = new ArrayList<>();
        LOGGER.info("Connecting to database. Method: getEndingIngredients()");
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {

            LOGGER.info("Successfully connected to DB");
            String sql = "SELECT * FROM WAREHOUSE WHERE WAREHOUSE.amount < 10";
            ResultSet resultSet = statement.executeQuery(sql);
            LOGGER.info("Result set is got");
            while (resultSet.next()) {
                Warehouse ingredient = createWarehouseIngred(resultSet);
                ingredientList.add(ingredient);
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return ingredientList;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean alreadyExist(Ingredient ingredient) {
        LOGGER.info("Connecting to database. Method:  alreadyExist(Ingredient ingredient) ");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM WAREHOUSE WHERE ingred_id = (SELECT id FROM INGREDIENT WHERE name = ?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, ingredient.getName());
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("ResultSet is got");
            if(resultSet.next()) {
                return true;
            }  else {
                LOGGER.info("Cannot find ingredient by this name");
                return false;
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    private Warehouse createWarehouseIngred(ResultSet resultSet) throws SQLException {
        Warehouse ingredient;
        ingredient = new Warehouse();
        ingredient.setId(resultSet.getInt("id"));
        ingredient.setIngredId(resultSet.getInt("ingred_id"));
        ingredient.setAmount(resultSet.getInt("amount"));
        return ingredient;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
