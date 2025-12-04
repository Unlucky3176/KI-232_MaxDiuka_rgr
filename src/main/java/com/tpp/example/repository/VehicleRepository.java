package com.tpp.example.repository;

import com.tpp.example.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {
    List<Vehicle> findAll();
    Optional<Vehicle> findById(Integer id);
    void save(Vehicle v);
    void update(Integer id, Vehicle v);
    void deleteById(Integer id);
    List<String> executeRawSql(String sql) throws Exception;
}