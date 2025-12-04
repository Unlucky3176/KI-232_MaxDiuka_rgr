package com.tpp.example.models;

public class Vehicle {
    private Integer vehicleId;
    private String brand;
    private String model;
    private String engineType;
    private Integer engineCapacity;

    public Vehicle() {}

    public Vehicle(Integer vehicleId, String brand, String model, String engineType, Integer engineCapacity) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
    }

    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getEngineType() { return engineType; }
    public void setEngineType(String engineType) { this.engineType = engineType; }
    public Integer getEngineCapacity() { return engineCapacity; }
    public void setEngineCapacity(Integer engineCapacity) { this.engineCapacity = engineCapacity; }
}