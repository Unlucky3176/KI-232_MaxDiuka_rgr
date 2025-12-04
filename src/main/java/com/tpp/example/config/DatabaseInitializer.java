package com.tpp.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @EventListener(ContextRefreshedEvent.class)
    public void initializeDatabase() {
        try {
            Integer vehicleCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM vehicle", Integer.class);
            Integer customerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customer", Integer.class);
            Integer saleCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM sale", Integer.class);
            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);

            if (vehicleCount == 0 && customerCount == 0 && saleCount == 0 && userCount == 0) {
                loadDataSql();
            }

        } catch (Exception e) {
            recreateTables();
            loadDataSql();
        }
    }

    private void recreateTables() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS customer (
                customer_id BIGSERIAL PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                phone VARCHAR(20) NOT NULL,
                email VARCHAR(100) NOT NULL
            );

            CREATE TABLE IF NOT EXISTS vehicle (
                vehicle_id BIGSERIAL PRIMARY KEY,
                brand VARCHAR(50) NOT NULL,
                model VARCHAR(50) NOT NULL,
                engine_type VARCHAR(50) NOT NULL,
                engine_capacity INTEGER NOT NULL
            );

            CREATE TABLE IF NOT EXISTS sale (
                sale_id BIGSERIAL PRIMARY KEY,
                sale_date DATE NOT NULL,
                customer_id BIGINT REFERENCES customer(customer_id),
                vehicle_id BIGINT REFERENCES vehicle(vehicle_id),
                amount INTEGER NOT NULL
            );
                  CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                username VARCHAR(100) UNIQUE NOT NULL,
                password VARCHAR(200) NOT NULL,
                role VARCHAR(50) NOT NULL
                            );
        """);
    }

    private void loadDataSql() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream("/data.sql")),
                        StandardCharsets.UTF_8))) {

            String sql = reader.lines().collect(Collectors.joining("\n"));
            jdbcTemplate.execute(sql);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
