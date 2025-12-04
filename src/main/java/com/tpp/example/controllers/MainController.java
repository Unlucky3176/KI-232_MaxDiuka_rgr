package com.tpp.example.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/home"})
    public String home(Authentication authentication, Model model) {
        String username = authentication != null ? authentication.getName() : null;
        model.addAttribute("username", username);
        return "home";
    }
}
