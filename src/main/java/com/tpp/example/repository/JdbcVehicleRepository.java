package com.tpp.example.repository;

import com.tpp.example.models.Vehicle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class JdbcVehicleRepository implements VehicleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVehicleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Vehicle> rowMapper = (rs, rowNum) -> {
        Vehicle v = new Vehicle();
        v.setVehicleId(rs.getInt("vehicle_id"));
        v.setBrand(rs.getString("brand"));
        v.setModel(rs.getString("model"));
        v.setEngineType(rs.getString("engine_type"));
        v.setEngineCapacity(rs.getInt("engine_capacity"));
        return v;
    };

    @Override
    public List<Vehicle> findAll() {
        String sql = "SELECT * FROM vehicle ORDER BY vehicle_id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Vehicle> findById(Integer id) {
        String sql = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        List<Vehicle> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void save(Vehicle v) {
        String sql = "INSERT INTO vehicle(brand, model, engine_type, engine_capacity) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, v.getBrand(), v.getModel(), v.getEngineType(), v.getEngineCapacity());
    }

    @Override
    public void update(Integer id, Vehicle v) {
        String sql = """
            UPDATE vehicle 
            SET brand=?, model=?, engine_type=?, engine_capacity=? 
            WHERE vehicle_id=?
            """;
        jdbcTemplate.update(sql, v.getBrand(), v.getModel(), v.getEngineType(), v.getEngineCapacity(), id);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM vehicle WHERE vehicle_id=?", id);
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
