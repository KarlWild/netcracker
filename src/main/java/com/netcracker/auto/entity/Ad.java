package com.netcracker.auto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adId;

    @ManyToOne
    @JoinColumn(name = "transport_id")
    private Transport transport;

    private Date yearOfIssue;
    private String color;
    private Integer mileage;
    private String stateNumber;
    private String vin;
    private String sts;
    private Integer numberOfOwners;
    private String address;
    private Boolean verified;
    private String status;
    private String description;
    private Long price;
    private String driveUnit;

    @OneToMany(mappedBy = "ad")
    private List<Photo> photos;

    public void addPhoto(Photo photo) {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        photos.add(photo);
        photo.setAd(this);
    }

    public enum Status{
        SOLD,
        ON_SALE
    }

    public enum DriveUnit{
        FRONT,
        REAR,
        FOUR
    }
}