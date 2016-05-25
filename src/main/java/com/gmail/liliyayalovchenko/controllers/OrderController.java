package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Order;
import com.gmail.liliyayalovchenko.jdbc.OrderDAO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class OrderController {

    private PlatformTransactionManager txManager;
    private OrderDAO orderDAO;

    @Transactional
    public void createOrder(Order order) {
        orderDAO.createOrder(order);
    }

    @Transactional
    public void addDishToOpenOrder(Dish dish, int orderNumber) {
        orderDAO.addDishToOpenOrder(dish, orderNumber);
    }

    @Transactional
    public void deleteOrder(int orderNumber) {
        orderDAO.deleteOrder(orderNumber);
    }

    @Transactional
    public void changeOrderStatus(int orderNumber) {
        orderDAO.changeOrderStatus(orderNumber);
    }

    @Transactional
    public List<Order> getOpenOrClosedOrder(String orderStatus) {
        return orderDAO.getOpenOrClosedOrder(orderStatus);
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
}
