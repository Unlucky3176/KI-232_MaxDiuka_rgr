package com.tpp.example.repository;

import com.tpp.example.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<User> mapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("role")
    );

    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        var list = jdbc.query("SELECT id, username, password, role FROM users WHERE username = ?",
                mapper, username);
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.get(0));
    }

    @Override
    public void save(User user) {
        jdbc.update("INSERT INTO users(username, password, role) VALUES (?, ?, ?)",
                user.getUsername(), user.getPassword(), user.getRole());
    }

    @Override
    public boolean existsByUsername(String username) {
        Integer cnt = jdbc.queryForObject("SELECT COUNT(*) FROM users WHERE username = ?", Integer.class, username);
        return cnt != null && cnt > 0;
    }
}
