package com.tpp.example.controllers;

import com.tpp.example.models.Customer;
import com.tpp.example.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("customers", service.getAll());
        model.addAttribute("newCustomer", new Customer());
        return "customers";
    }

    @PostMapping("/customers")
    public String addCustomer(@ModelAttribute Customer c) {
        service.create(c);
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomerForm(@PathVariable Integer id, Model model) {
        Customer c = service.getById(id).orElse(new Customer());
        model.addAttribute("customer", c);
        return "customer-edit";
    }

    @PostMapping("/customers/edit/{id}")
    public String editCustomerSave(@PathVariable Integer id, @ModelAttribute Customer customer) {
        service.update(id, customer);
        return "redirect:/customers";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam Integer id) {
        service.delete(id);
        return "redirect:/customers";
    }
}
