package com.gmail.liliyayalovchenko.jdbc;

import com.gmail.liliyayalovchenko.domains.ReadyMeal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

public class ReadyMealDAOImpl implements ReadyMealDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadyMealDAOImpl.class);

    private DataSource dataSource;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<ReadyMeal> getAllReadyMeals() {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addReadyMeal(ReadyMeal meal) {

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
