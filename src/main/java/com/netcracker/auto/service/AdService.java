package com.netcracker.auto.service;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
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
    public Optional<Ad> findById(Long id) {
        return adRepository.findById(id);
    }

    @Transactional
    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    @Transactional
    public List<Ad> findUnVerified(){
        List<Ad> list = adRepository.findByVerified();
        //list.sort(new AdService.ComparatorIgnoreCaseAndSpace());
        return list;
    }
    public void saveAd(Ad ad){
        adRepository.save(ad);
    }
}