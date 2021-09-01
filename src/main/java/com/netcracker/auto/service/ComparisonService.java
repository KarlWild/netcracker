package com.netcracker.auto.service;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.ComparisonAds;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.repository.ComparisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Popkov
 */
@Service
public class ComparisonService {
    @Autowired
    private ComparisonRepository comparisonRepository;

    public List<ComparisonAds> listComparisonAds(User user) {
        return comparisonRepository.findByUser(user);
    }

    public void saveAd(ComparisonAds ad) {
        comparisonRepository.save(ad);
    }

    public Optional<ComparisonAds> findComparisonByAdAndUser(Ad ad, User user) {
        return comparisonRepository.findByAdAndUser(ad, user);
    }

}
