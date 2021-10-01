package com.netcracker.auto.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FAVOURITE_ADS")
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="favourite_ad_id")
    private Integer favouriteId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user_id;

    @OneToOne
    @JoinColumn(name="ad_id")
    private Ad ad_id;

    public Integer getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(Integer favouriteId) {
        this.favouriteId = favouriteId;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Ad getAd() {
        return ad_id;
    }

    public void setAd(Ad ad) {
        this.ad_id = ad;
    }
}
