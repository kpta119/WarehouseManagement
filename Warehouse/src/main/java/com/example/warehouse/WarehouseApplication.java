package com.example.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class WarehouseApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Integer v = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
		if (v != null && v == 1) {
			System.out.println(" Connection works (SELECT 1 = " + v + ")");
		} else {
			System.err.println("✖ Connection does not work (Result: " + v + ")");
		}

	}
}