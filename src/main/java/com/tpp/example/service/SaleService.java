package com.tpp.example.service;

import com.tpp.example.models.Sale;
import com.tpp.example.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository repo;

    public SaleService(SaleRepository repo) {
        this.repo = repo;
    }

    public List<Sale> getAll() {
        return repo.findAll();
    }

    public Optional<Sale> getById(Integer id) {
        return repo.findById(id);
    }

    public void create(Sale s) {
        repo.save(s);
    }

    public void update(Integer id, Sale s) {
        repo.update(id, s);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<String> executeRawSql(String sql) throws Exception {
        return repo.executeRawSql(sql);
    }
}
