package com.tpp.example.repository;

import com.tpp.example.models.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        Customer c = new Customer();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setName(rs.getString("name"));
        c.setPhone(rs.getString("phone"));
        c.setEmail(rs.getString("email"));
        return c;
    };

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT customer_id, name, phone, email FROM customer ORDER BY customer_id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        String sql = "SELECT customer_id, name, phone, email FROM customer WHERE customer_id = ?";
        List<Customer> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void save(Customer c) {
        String sql = "INSERT INTO customer(name, phone, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, c.getName(), c.getPhone(), c.getEmail());
    }

    @Override
    public void update(Integer id, Customer c) {
        String sql = "UPDATE customer SET name = ?, phone = ?, email = ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, c.getName(), c.getPhone(), c.getEmail(), id);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<String> executeRawSql(String sql) throws Exception {
//        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
//             Statement st = conn.createStatement()) {
//
//            List<String> result = new ArrayList<>();
//            boolean hasRs = st.execute(sql);
//            if (hasRs) {
//                try (ResultSet rs = st.getResultSet()) {
//                    ResultSetMetaData md = rs.getMetaData();
//                    int cols = md.getColumnCount();
//                    StringBuilder header = new StringBuilder();
//                    for (int i = 1; i <= cols; i++) header.append(md.getColumnName(i)).append("\t");
//                    result.add(header.toString());
//                    while (rs.next()) {
//                        StringBuilder row = new StringBuilder();
//                        for (int i = 1; i <= cols; i++) {
//                            row.append(rs.getString(i)).append("\t");
//                        }
//                        result.add(row.toString());
//                    }
//                }
//            } else {
//                int uc = st.getUpdateCount();
//                result.add("Update count: " + uc);
//            }
//            return result;
//        }
        throw new UnsupportedOperationException("executeRawSql is disabled for security reasons. Use parameterized operations.");
    }
}
