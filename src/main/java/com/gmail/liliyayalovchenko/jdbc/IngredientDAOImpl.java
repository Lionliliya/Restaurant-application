package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IngredientDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientDAOImpl.class);

    private DataSource dataSource;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient getIngredientById(int id) {
        Ingredient ingredient;
        LOGGER.info("Connecting to database. Method: getIngredientById(int id)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM INGREDIENT WHERE id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("Result set is got");
            if (resultSet.next()) {
               ingredient = createIngredient(resultSet);
            } else {
                LOGGER.error("Cannot find ingredient bi this id");
                throw new RuntimeException("Cannot find ingredient bi this id");
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }

        return ingredient;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredientList = new ArrayList<>();
        LOGGER.info("Connecting to database. Method: getAllIngredients()");
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {

            LOGGER.info("Successfully connected to DB");
            String sql = "SELECT * FROM INGREDIENT";
            ResultSet resultSet = statement.executeQuery(sql);
            LOGGER.info("Result set is got");
            while (resultSet.next()) {
                Ingredient ingredient = createIngredient(resultSet);
                ingredientList.add(ingredient);
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return ingredientList;
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        Ingredient ingredient;
        LOGGER.info("Connecting to database. Method: getIngredientByName(String name)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM INGREDIENT WHERE name = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("Result set is got");
            if (resultSet.next()) {
                ingredient = createIngredient(resultSet);
            } else {
                LOGGER.error("Cannot find ingredient bi this id");
                throw new RuntimeException("Cannot find ingredient bi this id");
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean exist(Ingredient ingredient) {
        LOGGER.info("Connecting to database. Method: exist(Ingredient ingredient)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM INGREDIENT WHERE name = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, ingredient.getName());
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("Result set is got");
            return resultSet.next();

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addIngredient(Ingredient ingredient) {
        LOGGER.info("Connecting to database. Method: addIngredient(Ingredient ingredient)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO INGREDIENT (name) VALUE (?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, ingredient.getName());
            statement.executeUpdate();
            LOGGER.info("Ingredient added to DB");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    private Ingredient createIngredient(ResultSet resultSet) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(resultSet.getInt("id"));
        ingredient.setName(resultSet.getString("name"));
        return ingredient;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
