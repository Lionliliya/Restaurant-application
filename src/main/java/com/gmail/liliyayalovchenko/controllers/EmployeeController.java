package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.jdbc.EmployeeDAO;
import org.springframework.transaction.PlatformTransactionManager;

public class EmployeeController {

    private PlatformTransactionManager txManager;
    private EmployeeDAO employeeDAO;

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
}
