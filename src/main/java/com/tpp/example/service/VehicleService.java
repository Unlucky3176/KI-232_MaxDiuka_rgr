package com.tpp.example.service;

import com.tpp.example.models.Vehicle;
import com.tpp.example.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository repo;

    public VehicleService(VehicleRepository repo) {
        this.repo = repo;
    }

    public List<Vehicle> getAll() {
        return repo.findAll();
    }

    public Optional<Vehicle> getById(Integer id) {
        return repo.findById(id);
    }

    public void create(Vehicle v) {
        repo.save(v);
    }

    public void update(Integer id, Vehicle v) {
        repo.update(id, v);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<String> executeRawSql(String sql) throws Exception {
        return repo.executeRawSql(sql);
    }
}