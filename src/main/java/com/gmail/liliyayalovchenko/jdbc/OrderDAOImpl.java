package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(Order.class);

    private DataSource dataSource;
    private DishDAO dishDAO;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createOrder(Order order) {
        LOGGER.info("Connecting to database. Method: createOrder(Order order)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO ORDER_FROM_MENU (order_num, employee_id, table_num, " +
                            "order_date, status) values(?, ?, ?, ?, ?)")) {

            LOGGER.info("Successfully connected to DB");

            statement.setInt(1, order.getOrderNumber());
            statement.setInt(2, order.getEmployeeId());
            statement.setInt(3, order.getTableNumber());
            statement.setDate(4, (Date) order.getOrderDate());
            statement.setString(5, "opened");
            statement.executeUpdate();
            LOGGER.info("Order added");

            List<Dish> dishList = order.getDishList();
            for (Dish dish : dishList) {
                addDishIdToOrder(getOrderByOrderNum(order.getOrderNumber()).getId(), dish);
            }
            LOGGER.info("All dishes added to ORDER_DISH");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDishToOpenOrder(Dish dish, int orderNumber) {
        LOGGER.info("Connecting to database. Method: addDishToOpenOrder(Dish dish, int orderNumber)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO ORDER_DISH (order_id, dish_id) values(?, ?)")) {

            LOGGER.info("Successfully connected to DB");
            Order order = getOrderByOrderNum(orderNumber);
            if (!"closed".equals(order.getStatus())) {
                statement.setInt(1, order.getId());
                statement.setInt(2, dish.getId());
                statement.executeUpdate();
                LOGGER.info("Dish added to open Order number " + orderNumber);
            } else {
                LOGGER.info("Order status is closed. Cant add dish");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteOrder(int orderNumber) {
        LOGGER.info("Connecting to database. Method: deleteOrder(int orderNumber)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("DELETE FROM ORDER_FROM_MENU WHERE order_num = ?")) {

            LOGGER.info("Successfully connected to DB");
            Order order = getOrderByOrderNum(orderNumber);
            if (!"closed".equals(order.getStatus())) {
                deleteFromOrderDish(order.getId());

                statement.setInt(1, orderNumber);
                statement.executeUpdate();
                LOGGER.info("Order deleted");
            } else {
                LOGGER.info("Order " + orderNumber + " is closed and can not be deleted!!!");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeOrderStatus(int orderNumber) {
        LOGGER.info("Connecting to database. Method: changeOrderStatus(int orderNumber)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE ORDER_FROM_MENU SET status = ? WHERE order_num = ?")) {

            LOGGER.info("Successfully connected to DB");
            Order order = getOrderByOrderNum(orderNumber);
            if (!"closed".equals(order.getStatus())) {
                statement.setString(1, "closed");
                statement.setInt(1, orderNumber);
                statement.executeUpdate();
                LOGGER.info("Order status changed to 'closed' orderNum = " + orderNumber);
            } else {
                LOGGER.info("Order status is already 'closed' Cannot be change to open!!!!");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOpenOrClosedOrder(String orderStatus) {
        List<Order> orderList = new ArrayList<>();
        LOGGER.info("Connecting to database. Method: getOpenOrClosedOrder()");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM ORDER_FROM_MENU WHERE status = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, orderStatus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                createOrder(order, resultSet);
                List<Dish> dishList = getAllDishOfOrder(order.getId());
                order.setDishList(dishList);
                orderList.add(order);
            }

            LOGGER.info("Orders is goT");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return orderList;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private List<Dish> getAllDishOfOrder(int orderId) {
        List<Dish> dishList = new ArrayList<>();
        LOGGER.info("Connecting to database. Method: getAllDishOfOrder(int orderId)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT dish_id FROM ORDER_DISH WHERE order_id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishId = resultSet.getInt("dish_id");
                Dish dish = dishDAO.getDishById(dishId);
                dishList.add(dish);
            }
            LOGGER.info("Order deleted from ORDER_DISH table");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return dishList;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void deleteFromOrderDish(int orderId) {
        LOGGER.info("Connecting to database. Method: deleteFromOrderDish(int orderId)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("DELETE FROM ORDER_DISH WHERE order_id = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setInt(1, orderId);
            statement.executeUpdate();
            LOGGER.info("Order deleted from ORDER_DISH table");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }


    @Transactional(propagation = Propagation.MANDATORY)
    private void addDishIdToOrder(int orderId, Dish dish) {
        LOGGER.info("Connecting to database. Method: addDishIdToOrder(int orderId, Dish dish)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO ORDER_DISH (order_id, dish_id) values(?, ?)")) {

            LOGGER.info("Successfully connected to DB");

            statement.setInt(1, orderId);
            statement.setInt(2, dish.getId());
            statement.executeUpdate();
            LOGGER.info("Dish added to table ORDER_DISH");

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private Order getOrderByOrderNum(int orderNumber) {
        Order  order = new Order();
        LOGGER.info("Connecting to database. Method: getOrderByOrderNum(int orderNumber)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM ORDER_FROM_MENU WHERE order_num = ?")) {

            LOGGER.info("Successfully connected to DB");

            statement.setInt(1,orderNumber);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("Order is got");
            if (resultSet.next()) {
                createOrder(order, resultSet);
                LOGGER.info("Order is got");
            }
            return order;
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    private void createOrder(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getInt("id"));
        order.setOrderNumber(resultSet.getInt("order_num"));
        order.setEmployeeId(resultSet.getInt("employee_id"));
        order.setTableNumber(resultSet.getInt("table_num"));
        order.setOrderDate(resultSet.getDate("order_date"));
        order.setStatus(resultSet.getString("status"));
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDishDAO(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }
}
