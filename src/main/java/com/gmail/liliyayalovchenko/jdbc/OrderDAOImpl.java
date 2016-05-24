package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(Order.class);

    private DataSource dataSource;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createOrder(Order order) {

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDishToOpenOrder(Dish dish) {

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteOrder(Order order) {

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeOrderStatus(Order order) {

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOpenOrders() {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getClosedOrders() {
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
