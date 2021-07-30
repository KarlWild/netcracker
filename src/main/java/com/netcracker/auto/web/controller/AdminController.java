package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.service.AdService;
import com.netcracker.auto.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private AdService adService;
    private PhotoService photoService;
    @Autowired
    public AdminController(AdService adService, PhotoService photoService) {
        this.adService = adService;
        this.photoService = photoService;
    }
    @GetMapping
    public String getUnVerifiedAds(Model model) {
        model.addAttribute("admin", adService.findUnVerified());
        return "admin/admin";
    }
    @PostMapping("/approve/{id}")
    public String verifyAd(@PathVariable("id") int id) {
        Ad ad = adService.findById(id).get();
        ad.setStatus("Ок");
        ad.setVerified(true);
        adService.saveAd(ad);

        return "redirect:/admin";
    }
    @PostMapping("/reject/{id}")
    public String changeStatus(@PathVariable("id") int id) {
        Ad ad = adService.findById(id).get();
        ad.setStatus("Отклонено");
        adService.saveAd(ad);
        return "redirect:/admin";
    }
}
