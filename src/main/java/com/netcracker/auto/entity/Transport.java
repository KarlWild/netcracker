package com.netcracker.auto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transport_id")
    private Integer transportId;
    private String brand;
    private String model;
    @Column(name="generation_name")
    private String generationName;
    @Column(name="generation_years")
    private String generationYears;
    @Column(name="body_type")
    private String bodyType;
    @Column(name="specification_type")
    private String specificationType;
    @Column(name="specification_power")
    private String specificationPower;
    private String fuel;
    private String equipment;
    @Column(name="body_type_id")
    private String bodyTypeId;

    public Transport(Integer transportId, String brand, String model, String generationName, String generationYears, String bodyType, String specificationType, String specificationPower, String fuel, String equipment, String bodyTypeId, String type) {
        this.transportId = transportId;
        this.brand = brand;
        this.model = model;
        this.generationName = generationName;
        this.generationYears = generationYears;
        this.bodyType = bodyType;
        this.specificationType = specificationType;
        this.specificationPower = specificationPower;
        this.fuel = fuel;
        this.equipment = equipment;
        this.bodyTypeId = bodyTypeId;
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGenerationName() {
        return generationName;
    }

    public void setGenerationName(String generationName) {
        this.generationName = generationName;
    }

    public String getGenerationYears() {
        return generationYears;
    }

    public void setGenerationYears(String generationYears) {
        this.generationYears = generationYears;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getSpecificationType() {
        return specificationType;
    }

    public void setSpecificationType(String specificationType) {
        this.specificationType = specificationType;
    }

    public String getSpecificationPower() {
        return specificationPower;
    }

    public void setSpecificationPower(String specificationPower) {
        this.specificationPower = specificationPower;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getBodyTypeId() {
        return bodyTypeId;
    }

    public void setBodyTypeId(String bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public String getFullName(){
        return brand + ' ' + model + ' ' + generationName;
    }
}