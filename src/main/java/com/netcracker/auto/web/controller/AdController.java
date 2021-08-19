package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.*;
import com.netcracker.auto.repository.AdRepository;
import com.netcracker.auto.repository.FavouriteRepository;
import com.netcracker.auto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdController {

    private AdService adService;
    private PhotoService photoService;
    private TransportService transportService;
    private AdRepository adRepository;
    private UserService userService;
    private FavouriteRepository favouriteRepository;

    @Autowired
    public AdController(AdService adService, PhotoService photoService, TransportService transportService,
                        AdRepository adRepository,UserService userService, FavouriteRepository favouriteRepository) {
        this.userService = userService;
        this.adService = adService;
        this.photoService = photoService;
        this.transportService=transportService;
        this.adRepository=adRepository;
        this.favouriteRepository=favouriteRepository;
    }

    @GetMapping("ads/{id}")
    public String getAd(@PathVariable("id") int id, Model model) {
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

    @GetMapping("/ads")
    public String getAds(Model model) {
        model.addAttribute("ads", adService.findAll());
        return "ad/catalogAds";
    }

    @GetMapping("/ad/tmp")
    public String tmp() {
        return "ad/button";
    }

    @GetMapping("/new/{id}")
    public String newAd(@PathVariable("id") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport=transportService.findById(id).get();
        ad.setTransport(transport);
        return "ad/form";
    }

    @PostMapping("/ad/tmp")
    public String create(@RequestParam("transportId") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport=transportService.findById(id).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        ad.setUser_id(userService.findUserByEmail(currentPrincipalName));
        ad.setTransport(transport);
        adRepository.save(ad);
        return "redirect:/lk/my_ads";
    }


   @GetMapping("ads/{id}/edit")
    public String update(@PathVariable("id") int id, Model model) {
        Ad ad = adService.findById(id).get();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        ad.setUser_id(userService.findUserByEmail(currentPrincipalName));
        model.addAttribute("ad", ad);
        return "ad/edit";
    }

    @PostMapping("ads/{id}/edit")
    public String update(@PathVariable("id") int adId, @RequestParam("transportId") Integer id, @ModelAttribute("ad") Ad ad) {
        //ad=adService.findById(adId).get();
        Transport transport=transportService.findById(id).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        ad.setUser_id(userService.findUserByEmail(currentPrincipalName));
        ad.setTransport(transport);
        //adService.updateAd(adId, ad);
            adRepository.save(ad);
        return "redirect:/lk/my_ads";
    }

    //удаление
    @PostMapping("ads/{id}/remove")
    public String delete(@PathVariable("id") Integer adId, @ModelAttribute("ad") Ad ad) {
        ad=adService.findById(adId).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if(ad.getUser_id()==userService.findUserByEmail(currentPrincipalName))
        adRepository.delete(ad);
        return "redirect:/lk/my_ads";
    }

    //добавление в избранное
    @GetMapping("ads/{id}/favourite")
    public String addFavourite(@PathVariable("id") int adId, @ModelAttribute("favourite") Favourite f){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        f.setUser_id(userService.findUserByEmail(currentPrincipalName));
        f.setAd(adService.findById(adId).get());
        favouriteRepository.save(f);
        return "redirect:/lk/my_ads";
    }

    //отправка на модерацию
    @PostMapping("ads/{id}/check")
    public String check(@PathVariable("id") Integer adId, @ModelAttribute("ad") Ad ad) {
        ad=adService.findById(adId).get();
        ad.setStatus("open");
        adRepository.save(ad);
        return "redirect:/lk/my_ads";
    }

    //продано
    @PostMapping("ads/{id}/sold")
    public String sold(@PathVariable("id") Integer adId, @ModelAttribute("ad") Ad ad) {
        ad=adService.findById(adId).get();
        ad.setStatus(" продано ");
        adRepository.save(ad);
        return "redirect:/lk/my_ads";
    }
}


/*@PatchMapping("/ads/{id}/edit")
    public String change(@ModelAttribute("ad") Ad ad, @PathVariable("id") int adId, @RequestParam("transportId") int id){
        ad=adService.findById(adId).get();

        return "redirect:/lk/my_ads";
    }*/
