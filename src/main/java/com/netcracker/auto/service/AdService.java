package com.netcracker.auto.service;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Favourite;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AdService {

    private AdRepository adRepository;

    @Autowired
    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @Transactional
    public Optional<Ad> findById(int id) {
        return adRepository.findById(id);
    }

    @Transactional
    public List<Ad> findByUser(User user) {return adRepository.findByUser(user);}

    @Transactional
    public List<Ad> findAll() {
        return (List<Ad>) adRepository.findAll();
    }

    @Transactional
    public List<Ad> findUnVerified(){
        List<Ad> list = adRepository.findByVerifiedAndStatus("open");
        return list;
    }
    public void saveAd(Ad ad){
        adRepository.save(ad);
    }

    public void updateAd(int adId, Ad updatedAd){
        Ad ad=findById(adId).get();
        ad.setAddress(updatedAd.getAddress());
        ad.setColor(updatedAd.getColor());
        ad.setDescription(updatedAd.getDescription());
        ad.setDriveUnit(updatedAd.getDriveUnit());
        ad.setMileage(updatedAd.getMileage());
        ad.setNumberOfOwners(updatedAd.getNumberOfOwners());
        ad.setPrice(updatedAd.getPrice());
        ad.setStateNumber(updatedAd.getStateNumber());
        ad.setSts(updatedAd.getSts());
        ad.setVin(updatedAd.getVin());
        ad.setYearOfIssue(updatedAd.getYearOfIssue());

    }

}