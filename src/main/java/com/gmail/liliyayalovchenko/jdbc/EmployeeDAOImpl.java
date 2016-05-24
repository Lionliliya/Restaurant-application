package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    private DataSource dataSource;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addEmployee(Employee employee) {
        LOGGER.info("Connecting to database");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO EMPLOYEE (second_name, first_name, empl_date, phone, position, salary) VALUES (?, ?, ?, ?, ?, ?)")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, employee.getSecondName());
            statement.setString(2, employee.getFirstName());
            statement.setDate(3, (Date) employee.getEmplDate());
            statement.setString(4, employee.getPhone());
            statement.setString(5, employee.getPosition());
            statement.setInt(6, employee.getSalary());
            statement.executeUpdate();
            LOGGER.info("Employee is added");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeEmployee(String firstName, String secondName) {
        LOGGER.info("Connecting to database");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM EMPLOYEE WHERE first_name = ? AND second_name = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, firstName);
            statement.setString(2, secondName);

            statement.executeUpdate();
            LOGGER.info("Employee is deleted");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = null;
        LOGGER.info("Connecting to database");
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {

            LOGGER.info("Successfully connected to DB");
            String sql = "SELECT * FROM EMPLOYEE";
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Employee employee = createEmployee(resultSet);
                employeeList.add(employee);
            }
            LOGGER.info("Employees are got");
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee findEmployeeByName(String firstName, String secondName) {
        LOGGER.info("Connecting to database. Method findEmployeeByName(String firstName, String secondName)");
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM EMPLOYEE WHERE first_name = ? AND second_name = ?")) {

            LOGGER.info("Successfully connected to DB");
            statement.setString(1, firstName);
            statement.setString(2, secondName);

            ResultSet resultSet = statement.executeQuery();
           if(resultSet.next()) {
               Employee employee = createEmployee(resultSet);
               LOGGER.info("Employee are got");
               return employee;
           } else {
               LOGGER.info("Cannot find employee by these firstName and secondName");
               throw new RuntimeException("Cannot find employee by these firstName and secondName");
           }

        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to DB " + e);
            throw new RuntimeException(e);
        }
    }

    private Employee createEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setSecondName(resultSet.getString("second_name"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setEmplDate(resultSet.getDate("empl_date"));
        employee.setPhone(resultSet.getString("phone"));
        employee.setPosition(resultSet.getString("position"));
        employee.setSalary(resultSet.getInt("salary"));
        return employee;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
