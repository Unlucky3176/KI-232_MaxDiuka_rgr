package com.tpp.example.controllers;

import com.tpp.example.models.User;
import com.tpp.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Користувач з таким логіном вже існує");
            return "register";
        }
        String encoded = passwordEncoder.encode(password);
        userRepository.save(new User(null, username, encoded, "ROLE_USER"));
        return "redirect:/login?registered";

    }
}
