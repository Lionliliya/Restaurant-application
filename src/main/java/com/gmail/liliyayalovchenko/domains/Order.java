package com.gmail.liliyayalovchenko.domains;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int orderNumber;
    private int employeeId;
    private int tableNumber;
    private Date orderDate;
    private String status;
    private List<Dish> dishList;

    public Order(int orderNumber, int employeeId, int tableNumber, Date orderDate, String status, List<Dish> dishList) {
        this.orderNumber = orderNumber;
        this.employeeId = employeeId;
        this.tableNumber = tableNumber;
        this.orderDate = orderDate;
        this.status = status;
        this.dishList = dishList;
    }

    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", employeeId=" + employeeId +
                ", tableNumber=" + tableNumber +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", dishList=" + dishList +
                '}';
    }
}
