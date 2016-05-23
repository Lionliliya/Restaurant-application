package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Employee;

import java.util.List;

public interface EmployeeDAO {

    public void addEmployee(Employee employee);

    public void removeEmployee(String firstName, String secondName);

    public List<Employee> getAllEmployees();

    public Employee findEmployeeByName(String firstName, String secondName);

}
