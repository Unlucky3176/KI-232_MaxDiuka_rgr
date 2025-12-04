package com.tpp.example.controllers;

import com.tpp.example.models.Vehicle;
import com.tpp.example.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping("/vehicles")
    public String vehicles(Model model) {
        model.addAttribute("vehicles", service.getAll());
        model.addAttribute("newVehicle", new Vehicle());
        return "vehicles";
    }

    @PostMapping("/vehicles")
    public String addVehicle(@ModelAttribute Vehicle v) {
        service.create(v);
        return "redirect:/vehicles";
    }

    @GetMapping("/vehicles/edit/{id}")
    public String editVehicleForm(@PathVariable Integer id, Model model) {
        Vehicle v = service.getById(id).orElse(new Vehicle());
        model.addAttribute("vehicle", v);
        return "vehicle-edit";
    }

    @PostMapping("/vehicles/edit/{id}")
    public String editVehicleSave(@PathVariable Integer id, @ModelAttribute Vehicle vehicle) {
        service.update(id, vehicle);
        return "redirect:/vehicles";
    }

    @PostMapping("/vehicles/delete")
    public String deleteVehicle(@RequestParam Integer id) {
        service.delete(id);
        return "redirect:/vehicles";
    }
}
