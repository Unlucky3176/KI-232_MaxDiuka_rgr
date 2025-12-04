package com.tpp.example.repository;

import com.tpp.example.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<Customer> findAll();
    Optional<Customer> findById(Integer id);
    void save(Customer c);      // insert
    void update(Integer id, Customer c);
    void deleteById(Integer id);
    List<String> executeRawSql(String sql) throws Exception;
}
