package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.service.AdService;
import com.netcracker.auto.service.PhotoService;
import com.netcracker.auto.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String getUnVerifiedAds(Model model) {
        model.addAttribute("admin", adService.findUnVerified());
        return "admin/admin";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/approve/{id}")
    public String verifyAd(@PathVariable("id") int id) {
        Ad ad = adService.findById(id).get();
        ad.setStatus("closed");
        ad.setVerified(true);
        adService.saveAd(ad);

        return "redirect:/admin";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/reject/{id}")
    public String getChangeStatus(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);
        return "/admin/comment_form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/reject/{id}")
    public String changeStatus(@PathVariable("id") int id, @ModelAttribute("comment") @Valid String comment) {
        Ad ad = adService.findById(id).get();
        ad.setStatus(comment);
        adService.saveAd(ad);
        return "redirect:/admin";
    }

}
