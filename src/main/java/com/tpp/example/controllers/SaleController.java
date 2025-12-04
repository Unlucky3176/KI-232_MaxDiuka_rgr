package com.tpp.example.controllers;

import com.tpp.example.models.Sale;
import com.tpp.example.service.SaleService;
import com.tpp.example.service.CustomerService;
import com.tpp.example.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SaleController {

    private final SaleService service;
    private final CustomerService customerService;
    private final VehicleService vehicleService;

    public SaleController(SaleService service,
                          CustomerService customerService,
                          VehicleService vehicleService) {
        this.service = service;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/sales")
    public String sales(Model model) {
        model.addAttribute("sales", service.getAll());
        model.addAttribute("newSale", new Sale());

        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("vehicles", vehicleService.getAll());

        return "sales";
    }

    @GetMapping("/sales/edit/{id}")
    public String editSaleForm(@PathVariable Integer id, Model model) {
        Sale s = service.getById(id).orElse(new Sale());
        model.addAttribute("sale", s);

        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("vehicles", vehicleService.getAll());

        return "sale-edit";
    }

    @PostMapping("/sales/edit/{id}")
    public String editSaleSave(@PathVariable Integer id, @ModelAttribute Sale sale) {
        service.update(id, sale);
        return "redirect:/sales";
    }

    @PostMapping("/sales")
    public String addSale(@ModelAttribute Sale s) {
        service.create(s);
        return "redirect:/sales";
    }

    @PostMapping("/sales/delete")
    public String deleteSale(@RequestParam Integer id) {
        service.delete(id);
        return "redirect:/sales";
    }
}
