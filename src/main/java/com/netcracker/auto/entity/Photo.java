package com.netcracker.auto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//sdf
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
    private String fileName;

    @Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                //", adId=" + ad.getAdId() +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
