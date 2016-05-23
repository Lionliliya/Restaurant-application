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
        Ingredient ingredient = null;
        LOGGER.info("Connecting to database");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM INGREDIENT WHERE id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("Result set is got");
            if (resultSet.next()) {
               createIngredient(resultSet);
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
        LOGGER.info("Connecting to database");
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
