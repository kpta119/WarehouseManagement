package com.example.warehouse.dao.impl;

import com.example.warehouse.dao.CategoryDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class CategoryDaoImpl implements CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    public CategoryDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
