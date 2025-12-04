package com.tpp.example.models;

import java.time.LocalDate;

public class Sale {
    private Integer saleId;
    private LocalDate saleDate;
    private Integer customerId;
    private Integer vehicleId;
    private Integer amount;

    public Sale() {}

    public Sale(Integer saleId, LocalDate saleDate, Integer customerId, Integer vehicleId, Integer amount) {
        this.saleId = saleId;
        this.saleDate = saleDate;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.amount = amount;
    }

    public Integer getSaleId() { return saleId; }
    public void setSaleId(Integer saleId) { this.saleId = saleId; }
    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
}
