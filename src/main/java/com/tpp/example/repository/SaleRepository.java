package com.tpp.example.repository;

import com.tpp.example.models.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleRepository {
    List<Sale> findAll();
    Optional<Sale> findById(Integer id);
    void save(Sale s);
    void update(Integer id, Sale s);
    void deleteById(Integer id);
    List<String> executeRawSql(String sql) throws Exception;
}
