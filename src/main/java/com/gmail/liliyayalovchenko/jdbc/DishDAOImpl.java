package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDAOImpl implements DishDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DishDAOImpl.class);

    private DataSource dataSource;
    private IngredientDAO ingredientDAO;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDish(Dish dish) {
        LOGGER.info("Connecting to database. Method: addDish(Dish dish)");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("INSERT INTO DISH (name, category, price, weight) VALUES (?, ?, ?, ?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, dish.getName());
            statement.setString(2, dish.getCategory());
            statement.setDouble(3, dish.getPrice());
            statement.setInt(4, dish.getWeight());
            statement.executeUpdate();
            LOGGER.info("Dish is added");
            List<Ingredient> ingredients = dish.getIngredients();
            for (Ingredient ingredient : ingredients) {
                addIngredientsToDish(dish.getId(), ingredient);
            }
            LOGGER.info("All ingredients successfully added");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDish(Dish dish) {
        LOGGER.info("Connecting to database. Method: removeDish(Dish dish)");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("DELETE FROM DISH WHERE id = ?")) {

            LOGGER.info("Successfully connected to DB");
            deleteIngredientsOfDish(dish.getId());
            LOGGER.info("All ingredients successfully deleted");
            statement.setInt(1, dish.getId());
            statement.executeUpdate();
            LOGGER.info("Dish is deleted");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishByName(String dishName) {
        Dish dish;
        LOGGER.info("Connecting to database. Method: getDishByName(String dishName)");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT * FROM DISH WHERE name = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, dishName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = createDish(resultSet);
                LOGGER.info("Dish is got");
            } else {
                LOGGER.error("Cant get Dish by this name. Method: getDishByName(String dishName)");
                throw new RuntimeException("Cant get Dish by this name. Method: getDishByName(String dishName)");
            }

            List<Ingredient> ingredients = getAllIngredientsByDishId(dish.getId());
            dish.setIngredients(ingredients);
            LOGGER.info("All ingredients successfully added to Dish");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return dish;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        LOGGER.info("Connecting to database. Method: getAllDishes()");
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            LOGGER.info("Successfully connected to DB");
            String sql = "SELECT * FROM DISH";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Dish dish = createDish(resultSet);
                int id = dish.getId();
                LOGGER.info("Dish is got id: " + id);
                dish.setIngredients(getAllIngredientsByDishId(id));
                LOGGER.info("All ingredients successfully added to Dish id " + id);
                dishes.add(dish);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return dishes;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishById(int dish_id) {
        Dish dish;
        LOGGER.info("Connecting to database. Method: getDishById(int dish_id)");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT * FROM DISH WHERE id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, dish_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = createDish(resultSet);
                LOGGER.info("Dish is got");
            } else {
                LOGGER.error("Cant get Dish by this id. Method: getDishById(int dish_id)");
                throw new RuntimeException("Cant get Dish by this id. Method: getDishById(int dish_id)");
            }

            List<Ingredient> ingredients = getAllIngredientsByDishId(dish.getId());
            dish.setIngredients(ingredients);
            LOGGER.info("All ingredients successfully added to Dish");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return dish;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> getDishByMenuId(int menuId) {
        List<Dish> dishList = new ArrayList<>();
        LOGGER.info("Connecting to database. getDishByMenuId(int menuId)");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT * FROM MENU_DISH WHERE menu_id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, menuId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = getDishById(resultSet.getInt("dish_id"));

                LOGGER.info("Dish is got");
                dishList.add(dish);
            }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return dishList;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private Dish createDish(ResultSet resultSet) throws SQLException {
        Dish dish = new Dish();
        dish.setId(resultSet.getInt("id"));
        dish.setName(resultSet.getString("name"));
        dish.setCategory(resultSet.getString("category"));
        dish.setPrice(resultSet.getDouble("price"));
        dish.setWeight(resultSet.getInt("weight"));

        return dish;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void addIngredientsToDish(int dishId, Ingredient ingredient) {
        LOGGER.info("Connecting to database. Method: addIngredientsToDish(int dishId, Ingredient ingredient)");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("INSERT INTO DISH_INGTREDIENTS (dish_id, ingred_id) VALUES (?, ?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, dishId);
            statement.setInt(2, ingredient.getId());
            statement.executeUpdate();
            LOGGER.info("Ingredient is added to Table Dish_Ingredient");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void deleteIngredientsOfDish(int dishId) {
        LOGGER.info("Connecting to database. Method: deleteIngredientsOfDish(int id, Ingredient ingredient)");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("DELETE FROM DISH_INGTREDIENTS WHERE dish_id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, dishId);
            statement.executeUpdate();
            LOGGER.info("Ingredients are successfully deleted from Table Dish_Ingredient");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private List<Ingredient> getAllIngredientsByDishId(int dishId) {
        List<Ingredient> ingredients = new ArrayList<>();
        LOGGER.info("Connecting to database. Method: getAllIngredientsByDishId(int dishId)");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT * FROM DISH_INGTREDIENTS WHERE dish_id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, dishId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = ingredientDAO.getIngredientById(resultSet.getInt("ingred_id"));
                ingredients.add(ingredient);
            }
            LOGGER.info("All ingredients successfully received");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setIngredientDAO(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }
}
