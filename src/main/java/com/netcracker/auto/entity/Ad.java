package com.netcracker.auto.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="Ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ad_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="transport_id")
    private Transport transport;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user_id;

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
    private boolean verified;
    private String status="неактивно";

    public Ad(){}

    public Ad(Integer id, Transport transport, User user_id, Date yearOfIssue, String color, Integer mileage,
              String stateNumber, String vin, String sts, Integer numberOfOwners,
              String address, String description, Long price, String driveUnit,
              boolean verified, String status) {
        this.id = id;
        this.transport = transport;
        this.user_id = user_id;
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
        this.verified = verified;
        this.status = status;
    }

    @OneToMany(mappedBy = "ad", fetch = FetchType.EAGER)
    //@JoinColumn(name="ad")
    private List<Photo> photos;

    public void addPhoto(Photo photo) {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        photos.add(photo);
        photo.setAd(this);
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

    public void setYearOfIssue(Date yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
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

    public void setNumberOfOwners(Integer numberOfOwners) {
        this.numberOfOwners = numberOfOwners;
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

    public void setDriveUnit(String driveUnit) {
        this.driveUnit = driveUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

}

