package com.tpp.example.repository;

import com.tpp.example.models.Sale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

@Repository
public class JdbcSaleRepository implements SaleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSaleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Sale> rowMapper = (rs, rowNum) -> {
        Sale s = new Sale();
        s.setSaleId(rs.getInt("sale_id"));
        s.setSaleDate(rs.getDate("sale_date").toLocalDate());
        s.setCustomerId(rs.getInt("customer_id"));
        s.setVehicleId(rs.getInt("vehicle_id"));
        s.setAmount(rs.getInt("amount"));
        return s;
    };

    @Override
    public List<Sale> findAll() {
        return jdbcTemplate.query("SELECT * FROM sale ORDER BY sale_id", rowMapper);
    }

    @Override
    public Optional<Sale> findById(Integer id) {
        List<Sale> list = jdbcTemplate.query("SELECT * FROM sale WHERE sale_id=?", rowMapper, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void save(Sale s) {
        jdbcTemplate.update("""
            INSERT INTO sale(sale_date, customer_id, vehicle_id, amount) 
            VALUES (?, ?, ?, ?)
        """, s.getSaleDate(), s.getCustomerId(), s.getVehicleId(), s.getAmount());
    }

    @Override
    public void update(Integer id, Sale s) {
        jdbcTemplate.update("""
            UPDATE sale SET sale_date=?, customer_id=?, vehicle_id=?, amount=? 
            WHERE sale_id=?
        """, s.getSaleDate(), s.getCustomerId(), s.getVehicleId(), s.getAmount(), id);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM sale WHERE sale_id=?", id);
    }

    @Override
    public List<String> executeRawSql(String sql) throws Exception {
//        try (Connection conn = jdbcTemplate.getDataSource().getConnection();
//             Statement st = conn.createStatement()) {
//
//            List<String> result = new ArrayList<>();
//            boolean hasRs = st.execute(sql);
//
//            if (hasRs) {
//                try (ResultSet rs = st.getResultSet()) {
//                    ResultSetMetaData md = rs.getMetaData();
//                    int cols = md.getColumnCount();
//
//                    StringBuilder header = new StringBuilder();
//                    for (int i = 1; i <= cols; i++) header.append(md.getColumnName(i)).append("\t");
//                    result.add(header.toString());
//
//                    while (rs.next()) {
//                        StringBuilder row = new StringBuilder();
//                        for (int i = 1; i <= cols; i++) {
//                            row.append(rs.getString(i)).append("\t");
//                        }
//                        result.add(row.toString());
//                    }
//                }
//            } else {
//                result.add("Update count: " + st.getUpdateCount());
//            }
//
//            return result;
//        }
        throw new UnsupportedOperationException("executeRawSql is disabled for security reasons. Use parameterized operations.");
    }
}
