package com.example.warehouse.dao.impl;

import com.example.warehouse.dao.CountryDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class CountryDaoImpl implements CountryDao {
    private final JdbcTemplate jdbcTemplate;

    public CountryDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
