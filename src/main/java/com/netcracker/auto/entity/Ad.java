package com.netcracker.auto.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "Ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ad_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="transport_id")
    private Transport transport;

    @Column(name = "year_Of_Issue")
    private Date yearOfIssue;
    private String color;
    private Integer mileage;
    @Column(name = "state_Number")
    private String stateNumber;
    private String vin;
    private String sts;
    @Column(name = "number_Of_Owners")
    private Integer numberOfOwners;
    private String address;
    private String description;
    private Long price;
    @Column(name = "drive_Unit")
    private String driveUnit;
//    private boolean verified;
//    private String status;

    public Ad(){}

    public Ad(Integer id, Transport transport, Date yearOfIssue, String color, Integer mileage, String stateNumber, String vin, String sts, Integer numberOfOwners, String address, String description, Long price, String driveUnit) {
        this.id = id;
        this.transport = transport;
        this.yearOfIssue = yearOfIssue;
        this.color = color;
        this.mileage = mileage;
        this.stateNumber = stateNumber;
        this.vin = vin;
        this.sts = sts;
        this.numberOfOwners = numberOfOwners;
        this.address = address;
        this.description = description;
        this.price = price;
        this.driveUnit = driveUnit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Date getYearOfIssue() {
        return yearOfIssue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public Integer getNumberOfOwners() {
        return numberOfOwners;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDriveUnit() {
        return driveUnit;
    }

}

