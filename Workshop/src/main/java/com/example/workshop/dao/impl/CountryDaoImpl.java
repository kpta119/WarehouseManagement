package com.example.workshop.dao.impl;

import com.example.workshop.dao.CountryDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class CountryDaoImpl implements CountryDao {
    private final JdbcTemplate jdbcTemplate;

    public CountryDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
