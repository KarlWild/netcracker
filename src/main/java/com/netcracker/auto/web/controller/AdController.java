package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Photo;
import com.netcracker.auto.service.AdService;
import com.netcracker.auto.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ads")
public class AdController {

    private AdService adService;
    private PhotoService photoService;

    @Autowired
    public AdController(AdService adService, PhotoService photoService) {
        this.adService = adService;
        this.photoService = photoService;
    }

    @GetMapping("/{id}")
    public String getAd(@PathVariable("id") Long id, Model model) {
        Ad ad = adService.findById(id).get();

        Photo preview;
        List<Ad> ads = new ArrayList<>();

        if (ad.getPhotos().isEmpty()) {
            preview = photoService.getNoPhoto();
        }
        else {
            preview = ad.getPhotos().get(0);
            ad.getPhotos().remove(0);
            ads.add(ad);
        }

        model.addAttribute("preview", preview);
        model.addAttribute("ads", ads);
        model.addAttribute("ad", ad);
        return "ad/ad";
    }

    @GetMapping
    public String getAds(Model model) {
        model.addAttribute("ads", adService.findAll());
        return "ad/ads";
    }
}