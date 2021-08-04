package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Photo;
import com.netcracker.auto.entity.Transport;
import com.netcracker.auto.repository.AdRepository;
import com.netcracker.auto.service.AdService;
import com.netcracker.auto.service.PhotoService;
import com.netcracker.auto.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ads")
public class AdController {

    private AdService adService;
    private PhotoService photoService;
    @Autowired
    private TransportService transportService;
    @Autowired
    private AdRepository adRepository;

    @Autowired
    public AdController(AdService adService, PhotoService photoService) {
        this.adService = adService;
        this.photoService = photoService;

    }

    @GetMapping("/{id}")
    public String getAd(@PathVariable("id") Integer id, Model model) {
        Ad ad = adService.findById(id).get();

        Photo preview;
        List<Ad> ads = new ArrayList<>();

        /*if (ad.getPhotos().isEmpty()) {
            preview = photoService.getNoPhoto();
        }
        else {
            preview = ad.getPhotos().get(0);
            ad.getPhotos().remove(0);
            ads.add(ad);
        }*/

        //model.addAttribute("preview", preview);
        model.addAttribute("ads", ads);
        model.addAttribute("ad", ad);
        return "ad/ad";
    }

    @GetMapping
    public String getAds(Model model) {
        model.addAttribute("ads", adService.findAll());
        return "ad/ads";
    }


    @GetMapping("/ad/tmp")
    public String tmp() {
        return "ad/button";
    }

    //записывается в бд без transport_id
  /* @GetMapping("/new")
    public String newForm( @ModelAttribute("ad") Ad ad) {
        return "ad/form";
    }

    @PostMapping("/ad/tmp")
    public String create(@ModelAttribute("ad")  Ad ad) {
        adRepository.save(ad);
        return "redirect:tmp";
    }*/



///////////////////////////////////////////////////////////

    //не добавляется transport_id, не знаю как пофиксить
    @GetMapping("/new/{id}")
    public String newForm(@PathVariable("id") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport=transportService.findById(id).get();
        ad.setTransport(transport);
        //adRepository.save(ad);
        return "ad/form";
    }

    @PostMapping("/ad/tmp")
    public String create(@RequestParam("transportId") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport=transportService.findById(id).get();
        ad.setTransport(transport);
        adRepository.save(ad);
        return "redirect:tmp";
    }
}