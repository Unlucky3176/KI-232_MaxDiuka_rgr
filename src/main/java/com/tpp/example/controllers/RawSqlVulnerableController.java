package com.tpp.example.controllers;

import com.tpp.example.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RawSqlVulnerableController {

    private final VehicleService vehicleService;

    public RawSqlVulnerableController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping("/raw-sql")
    public String rawSqlDisabled(Model model) {
        model.addAttribute("message", "Виконання сирого SQL заборонено з міркувань безпеки.");
        return "raw-sql-disabled";
    }

    @PostMapping("/raw-sql")
    public String execRawSql(@RequestParam(name = "sql", required = false) String sql, Model model) {
        model.addAttribute("sql", sql == null ? "" : sql);
        try {
            // ВАЖЛИВО: executeRawSql використовує Statement.execute(...) і тому уразливий до SQLi
            List<String> out = vehicleService.executeRawSql(sql);
            model.addAttribute("result", out);
            model.addAttribute("error", null);
        } catch (Exception e) {
            model.addAttribute("result", null);
            model.addAttribute("error", "SQL ERROR: " + e.getMessage());
        }
        return "raw-sql";
    }
}
