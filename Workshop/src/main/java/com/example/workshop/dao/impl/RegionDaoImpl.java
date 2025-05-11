package com.example.workshop.dao.impl;

import com.example.workshop.dao.RegionDao;
import com.example.workshop.domain.Region;
import org.springframework.jdbc.core.JdbcTemplate;

public class RegionDaoImpl implements RegionDao {
    private final JdbcTemplate jdbcTemplate;

    public RegionDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Region region){
        jdbcTemplate.update("INSERT INTO REGION (id, name) VALUES (?, ?)",
                region.getId(), region.getName());
    }
}
