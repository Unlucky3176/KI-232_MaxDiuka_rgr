package com.tpp.example.service;

import com.tpp.example.models.Customer;
import com.tpp.example.models.Sale;
import com.tpp.example.models.Vehicle;
import com.tpp.example.utils.CommandParser.Command;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommandExecutor {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final SaleService saleService;

    public CommandExecutor(CustomerService customerService,
                           VehicleService vehicleService,
                           SaleService saleService) {
        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.saleService = saleService;
    }

    public String execute(Command cmd) throws Exception {
        String table = cmd.table;
        String action = cmd.action;
        Map<String, String> f = cmd.fields;
        switch (table) {
            case "customer":
                return execCustomer(action, f);
            case "vehicle":
                return execVehicle(action, f);
            case "sale":
                return execSale(action, f);
            default:
                throw new UnsupportedOperationException("Unknown table: " + table);
        }
    }

    private String execCustomer(String action, Map<String,String> f) throws Exception {
        switch (action) {
            case "insert": {
                Customer c = new Customer();
                if (f.containsKey("id")) c.setCustomerId(Integer.valueOf(f.get("id")));
                if (f.containsKey("name")) c.setName(f.get("name"));
                if (f.containsKey("phone")) c.setPhone(f.get("phone"));
                if (f.containsKey("email")) c.setEmail(f.get("email"));
                customerService.create(c);
                return "Customer inserted";
            }
            case "delete": {
                if (!f.containsKey("id")) throw new IllegalArgumentException("id required for delete");
                Integer id = Integer.valueOf(f.get("id"));
                customerService.delete(id);
                return "Customer deleted id=" + id;
            }
            case "update": {
                if (!f.containsKey("id")) throw new IllegalArgumentException("id required for update");
                Integer id = Integer.valueOf(f.get("id"));
                Customer c = new Customer();
                if (f.containsKey("name")) c.setName(f.get("name"));
                if (f.containsKey("phone")) c.setPhone(f.get("phone"));
                if (f.containsKey("email")) c.setEmail(f.get("email"));
                customerService.update(id, c);
                return "Customer updated id=" + id;
            }
            case "read": {
                if (f.containsKey("id")) {
                    Optional<Customer> oc = customerService.getById(Integer.valueOf(f.get("id")));
                    return oc.map(this::formatCustomerSingle).orElse("Not found");
                } else {
                    return formatCustomerList(customerService.getAll());
                }
            }
            default:
                throw new UnsupportedOperationException("Unknown action: " + action);
        }
    }

    private String execVehicle(String action, Map<String,String> f) throws Exception {
        switch (action) {
            case "insert": {
                Vehicle v = new Vehicle();
                if (f.containsKey("id")) v.setVehicleId(Integer.valueOf(f.get("id")));
                if (f.containsKey("brand")) v.setBrand(f.get("brand"));
                if (f.containsKey("model")) v.setModel(f.get("model"));
                if (f.containsKey("engine_type")) v.setEngineType(f.get("engine_type"));
                if (f.containsKey("engine_capacity")) v.setEngineCapacity(f.containsKey("engine_capacity") ? Integer.valueOf(f.get("engine_capacity")) : null);
                vehicleService.create(v);
                return "Vehicle inserted";
            }
            case "delete": {
                if (!f.containsKey("id")) throw new IllegalArgumentException("id required for delete");
                vehicleService.delete(Integer.valueOf(f.get("id")));
                return "Vehicle deleted";
            }
            case "update": {
                if (!f.containsKey("id")) throw new IllegalArgumentException("id required for update");
                Integer id = Integer.valueOf(f.get("id"));
                Vehicle v = new Vehicle();
                if (f.containsKey("brand")) v.setBrand(f.get("brand"));
                if (f.containsKey("model")) v.setModel(f.get("model"));
                if (f.containsKey("engine_type")) v.setEngineType(f.get("engine_type"));
                if (f.containsKey("engine_capacity")) v.setEngineCapacity(Integer.valueOf(f.get("engine_capacity")));
                vehicleService.update(id, v);
                return "Vehicle updated id=" + id;
            }
            case "read": {
                if (f.containsKey("id")) {
                    return vehicleService.getById(Integer.valueOf(f.get("id"))).map(this::formatVehicleSingle).orElse("Not found");
                } else {
                    return formatVehicleList(vehicleService.getAll());
                }
            }
            default:
                throw new UnsupportedOperationException("Unknown action: " + action);
        }
    }

    private String execSale(String action, Map<String,String> f) throws Exception {
        switch (action) {
            case "insert": {
                Sale s = new Sale();
                if (f.containsKey("id")) s.setSaleId(Integer.valueOf(f.get("id")));
                if (f.containsKey("sale_date")) s.setSaleDate(LocalDate.parse(f.get("sale_date")));
                if (f.containsKey("customer_id")) s.setCustomerId(Integer.valueOf(f.get("customer_id")));
                if (f.containsKey("vehicle_id")) s.setVehicleId(Integer.valueOf(f.get("vehicle_id")));
                if (f.containsKey("amount")) s.setAmount(Integer.valueOf(f.get("amount")));
                saleService.create(s);
                return "Sale inserted";
            }
            case "delete": {
                if (!f.containsKey("id")) throw new IllegalArgumentException("id required for delete");
                saleService.delete(Integer.valueOf(f.get("id")));
                return "Sale deleted";
            }
            case "update": {
                if (!f.containsKey("id")) throw new IllegalArgumentException("id required for update");
                Integer id = Integer.valueOf(f.get("id"));
                Sale s = new Sale();
                if (f.containsKey("sale_date")) s.setSaleDate(LocalDate.parse(f.get("sale_date")));
                if (f.containsKey("customer_id")) s.setCustomerId(Integer.valueOf(f.get("customer_id")));
                if (f.containsKey("vehicle_id")) s.setVehicleId(Integer.valueOf(f.get("vehicle_id")));
                if (f.containsKey("amount")) s.setAmount(Integer.valueOf(f.get("amount")));
                saleService.update(id, s);
                return "Sale updated id=" + id;
            }
            case "read": {
                if (f.containsKey("id")) {
                    return saleService.getById(Integer.valueOf(f.get("id"))).map(this::formatSaleSingle).orElse("Not found");
                } else {
                    return formatSaleList(saleService.getAll());
                }
            }
            default:
                throw new UnsupportedOperationException("Unknown action: " + action);
        }
    }

    private String formatCustomerList(List<Customer> list) {
        if (list == null || list.isEmpty()) return "No customers";
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Customer c : list) {
            sb.append(i++)
                    .append(") id=").append(nullSafeInt(c.getCustomerId()))
                    .append(" | ").append(nullSafe(c.getName()))
                    .append(" | ").append(nullSafe(c.getPhone()))
                    .append(" | ").append(nullSafe(c.getEmail()))
                    .append("\n");
        }
        return sb.toString();
    }

    private String formatVehicleList(List<Vehicle> list) {
        if (list == null || list.isEmpty()) return "No vehicles";
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Vehicle v : list) {
            sb.append(i++)
                    .append(") id=").append(nullSafeInt(v.getVehicleId()))
                    .append(" | ").append(nullSafe(v.getBrand())).append(" ").append(nullSafe(v.getModel()))
                    .append(" | ").append(nullSafe(v.getEngineType()))
                    .append(" | ").append(nullSafeInt(v.getEngineCapacity()))
                    .append("\n");
        }
        return sb.toString();
    }

    private String formatSaleList(List<Sale> list) {
        if (list == null || list.isEmpty()) return "No sales";
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Sale s : list) {
            sb.append(i++)
                    .append(") id=").append(nullSafeInt(s.getSaleId()))
                    .append(" | date=").append(s.getSaleDate() == null ? "null" : s.getSaleDate().toString())
                    .append(" | customerId=").append(nullSafeInt(s.getCustomerId()))
                    .append(" | vehicleId=").append(nullSafeInt(s.getVehicleId()))
                    .append(" | amount=").append(nullSafeInt(s.getAmount()))
                    .append("\n");
        }
        return sb.toString();
    }

    private String formatCustomerSingle(Customer c) {
        return "id=" + nullSafeInt(c.getCustomerId()) + " | " + nullSafe(c.getName()) + " | " + nullSafe(c.getPhone()) + " | " + nullSafe(c.getEmail());
    }

    private String formatVehicleSingle(Vehicle v) {
        return "id=" + nullSafeInt(v.getVehicleId()) + " | " + nullSafe(v.getBrand()) + " " + nullSafe(v.getModel()) + " | " + nullSafe(v.getEngineType()) + " | " + nullSafeInt(v.getEngineCapacity());
    }

    private String formatSaleSingle(Sale s) {
        return "id=" + nullSafeInt(s.getSaleId()) + " | date=" + (s.getSaleDate() == null ? "null" : s.getSaleDate().toString()) + " | customerId=" + nullSafeInt(s.getCustomerId()) + " | vehicleId=" + nullSafeInt(s.getVehicleId()) + " | amount=" + nullSafeInt(s.getAmount());
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private int nullSafeInt(Integer v) {
        return v == null ? 0 : v;
    }
}
