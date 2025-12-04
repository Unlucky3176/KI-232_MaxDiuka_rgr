package com.tpp.example.service;

import com.tpp.example.models.User;
import com.tpp.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        String encoded = passwordEncoder.encode(rawPassword);
        userRepository.save(new User(null, username, encoded, "ROLE_USER"));
        return true;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
