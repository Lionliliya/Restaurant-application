package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.domains.Employee;
import com.gmail.liliyayalovchenko.jdbc.EmployeeDAO;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmployeeController {

    private PlatformTransactionManager txManager;
    private EmployeeDAO employeeDAO;

    @Transactional
    public void addEmployee(Employee employee) {
        employeeDAO.addEmployee(employee);
    }

    @Transactional
    public void removeEmployee(String firstName, String secondName) {
        employeeDAO.removeEmployee(firstName, secondName);
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Transactional
    public Employee findEmployeeByName(String firstName, String secondName) {
        return employeeDAO.findEmployeeByName(firstName, secondName);
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
}
