package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.ReadyMeal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadyMealDAOImpl implements ReadyMealDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadyMealDAOImpl.class);

    private DataSource dataSource;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<ReadyMeal> getAllReadyMeals() {
        List<ReadyMeal> mealList = new ArrayList<>();
        LOGGER.info("Connecting to database. Method: getAllReadyMeals()");
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {

            LOGGER.info("Successfully connected to DB");
            String sql = " (SELECT  * FROM READY_MEALS)";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ReadyMeal readyMeal = new ReadyMeal();
                readyMeal.setId(resultSet.getInt("id"));
                readyMeal.setDishNumber(resultSet.getInt("dish_numb"));
                readyMeal.setDishId(resultSet.getInt("dish_id"));
                readyMeal.setEmployeeId(resultSet.getInt("employee_id"));
                readyMeal.setOrderId(resultSet.getInt("order_id"));
                readyMeal.setMealDate(resultSet.getDate("meal_date"));
                mealList.add(readyMeal);
            }
            LOGGER.info("List of all ready meals id got");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return mealList;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addReadyMeal(ReadyMeal meal) {
        LOGGER.info("Connecting to database. Method: addReadyMeal(ReadyMeal meal)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO READY_MEALS (dish_numb, dish_id, employee_id, order_id, meal_date) " +
                            "values(?, ?, ?, ?, ?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, meal.getDishNumber());
            statement.setInt(2, meal.getDishId());
            statement.setInt(3, meal.getEmployeeId());
            statement.setInt(4, meal.getOrderId());
            statement.setDate(5, new Date(meal.getMealDate().getTime()));
            statement.executeUpdate();
            LOGGER.info("Ready Meal added");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
