package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.service.AdService;
import com.netcracker.auto.service.PhotoService;
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
    class Comment{
        AdminController adminController;
        private String text;
        public Comment(AdminController adminController){
            this.adminController = adminController;
            text = "";
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
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
        ad.setStatus("Активно");
        ad.setVerified(true);
        adService.saveAd(ad);

        return "redirect:/admin";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/reject/{id}")
    public String getChangeStatus(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);
        Comment comment = new Comment(this);
        model.addAttribute("comment", comment);
        return "/admin/comment_form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/reject/{id}")
    public String changeStatus(@PathVariable("id") int id, @ModelAttribute("comment") @Valid Comment comment) {
        Ad ad = adService.findById(id).get();
        ad.setStatus("\nОтвет администратора: " + comment.getText());
        adService.saveAd(ad);
        return "redirect:/admin";
    }

}
