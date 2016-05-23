package com.gmail.liliyayalovchenko.domains;

import java.util.Date;

public class ReadyMeal {
    private int id;
    private int dishNumber;
    private int dishId;
    private int employeeId;
    private int orderId;
    private Date mealDate;

    public ReadyMeal(int dishNumber, int dishId, int employeeId, int orderId, Date mealDate) {
        this.dishNumber = dishNumber;
        this.dishId = dishId;
        this.employeeId = employeeId;
        this.orderId = orderId;
        this.mealDate = mealDate;
    }

    public ReadyMeal() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(int dishNumber) {
        this.dishNumber = dishNumber;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getMealDate() {
        return mealDate;
    }

    public void setMealDate(Date mealDate) {
        this.mealDate = mealDate;
    }

    @Override
    public String toString() {
        return "ReadyMeal{" +
                "id=" + id +
                ", dishNumber=" + dishNumber +
                ", dishId=" + dishId +
                ", employeeId=" + employeeId +
                ", orderId=" + orderId +
                ", mealDate=" + mealDate +
                '}';
    }
}
