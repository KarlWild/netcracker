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
    private Integer transportId;

    private String brand;
    private String model;
    private String generationName;
    private String generationYears;
    private String bodyType;
    private String specificationType;
    private String specificationPower;
    private String fuel;
    private String equipment;
    private String bodyTypeId;

}