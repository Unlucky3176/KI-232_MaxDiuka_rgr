package com.tpp.example.service;

import com.tpp.example.models.Customer;
import com.tpp.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Optional<Customer> getById(Integer id) {
        return repo.findById(id);
    }

    public void create(Customer c) {
        repo.save(c);
    }

    public void update(Integer id, Customer c) {
        repo.update(id, c);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<String> executeRawSql(String sql) throws Exception {
        return repo.executeRawSql(sql);
    }
}