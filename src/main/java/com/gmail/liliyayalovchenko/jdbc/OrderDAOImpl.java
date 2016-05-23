package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Order;

import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public void createOrder(Order order) {

    }

    @Override
    public void addDishToOpenOrder(Dish dish) {

    }

    @Override
    public void deleteOrder(Order order) {

    }

    @Override
    public void changeOrderStatus(Order order) {

    }

    @Override
    public List<Order> getOpenOrders() {
        return null;
    }

    @Override
    public List<Order> getClosedOrders() {
        return null;
    }
}
