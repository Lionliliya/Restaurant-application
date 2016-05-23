package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Order;

import java.util.List;

public interface OrderDAO {

    public void createOrder(Order order);

    public void addDishToOpenOrder(Dish dish);

    public void deleteOrder(Order order);

    public void changeOrderStatus(Order order);

    public List<Order> getOpenOrders();

    public List<Order> getClosedOrders();
}
