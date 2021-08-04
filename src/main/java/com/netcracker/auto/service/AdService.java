package com.netcracker.auto.service;

import com.netcracker.auto.entity.Ad;
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
    public List<Ad> findAll() {
        return (List<Ad>) adRepository.findAll();
    }

    @Transactional
    public List<Ad> findUnVerified(){
        //List<Ad> list = adRepository.findByVerifiedAndStatus("Отправлено на модерацию");
        //list.sort(new AdService.ComparatorIgnoreCaseAndSpace());
       // return list;
        return null;
    }
    public void saveAd(Ad ad){
        adRepository.save(ad);
    }
}