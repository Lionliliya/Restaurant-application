package com.gmail.liliyayalovchenko.domains;

import java.util.Date;

public class Employee {

    private int id;
    private String secondName;
    private String firstName;
    private Date emplDate;
    private String phone;
    private String position;
    private int salary;

    public Employee(String secondName, String firstName, Date emplDate, String phone, String position, int salary) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.emplDate = emplDate;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
    }

    public Employee() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getEmplDate() {
        return emplDate;
    }

    public void setEmplDate(Date emplDate) {
        this.emplDate = emplDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", secondName='" + secondName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", emplDate=" + emplDate +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                '}';
    }
}
