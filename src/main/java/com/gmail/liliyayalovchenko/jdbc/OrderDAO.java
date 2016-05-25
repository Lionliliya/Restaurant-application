package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Dish;
import com.gmail.liliyayalovchenko.domains.Order;

import java.util.List;

public interface OrderDAO {

    public void createOrder(Order order);

    public void addDishToOpenOrder(Dish dish, int orderNumber);

    public void deleteOrder(int orderNumber);

    public void changeOrderStatus(int orderNumber);

    public List<Order> getOpenOrClosedOrder(String orderStatus);


}
