package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.*;
import com.netcracker.auto.repository.AdRepository;
import com.netcracker.auto.repository.ReviewRepository;
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
//@RequestMapping("/ads")
public class AdController {

    private AdService adService;
    private PhotoService photoService;
    private TransportService transportService;
    private AdRepository adRepository;
    private UserService userService;
    private ReviewRepository reviewRepository;

    @Autowired
    public AdController(AdService adService, PhotoService photoService, TransportService transportService,
                        AdRepository adRepository, UserService userService, ReviewRepository reviewRepository) {
        this.userService = userService;
        this.adService = adService;
        this.photoService = photoService;
        this.transportService = transportService;
        this.adRepository = adRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("ads/{id}")
    public String getAd(@ModelAttribute("user") User user,
                        Principal principal, @PathVariable("id") int id, Model model) {
        User loggedUser = userService.findUserByEmail(principal.getName());
        Ad ad = adService.findById(id).get();

        Photo preview;
        List<Ad> ads = new ArrayList<>();

        if (ad.getPhotos().isEmpty()) {
            preview = photoService.getNoPhoto();
        } else {
            preview = ad.getPhotos().get(0);
            ad.getPhotos().remove(0);
            ads.add(ad);
        }

        model.addAttribute("preview", preview);
        model.addAttribute("ads", ads);
        model.addAttribute("ad", ad);

        Review review = new Review();
        review.setGiver(principal.getName());
        review.setUsername(ad.getUser_id().getEmail());
        review.setRating(0.0);
        review.setText(" ");

        model.addAttribute("review", review);
        return "ad/ad";
    }

    @PostMapping("/ads/reviews/post")
    public String postReview (@ModelAttribute("review") Review review) {
        reviewRepository.save(review);
        return "redirect:/ads";
    }

    @GetMapping("/ads")
    public String getAds(Model model, String keyword, String brand, String carModel,
                         Integer yearStart, Integer yearEnd,
                         Integer priceStart, Integer priceEnd,
                         Integer mileageStart, Integer mileageEnd,
                         Integer ownersCount) {
        List<Ad> ads = adService.findByAnyCriteria(brand, carModel,
                yearStart, yearEnd, priceStart, priceEnd, mileageStart, mileageEnd, ownersCount);

        model.addAttribute("brands", transportService.findDistinctBrand());
        model.addAttribute("ads", ads);
        return "ad/catalogAds";
    }

    @GetMapping("/ad/tmp")
    public String tmp() {
        return "ad/button";
    }

    @GetMapping("/new/{id}")
    public String newAd(@PathVariable("id") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport = transportService.findById(id).get();
        ad.setTransport(transport);
        return "ad/form";
    }

    @PostMapping("/ad/tmp")
    public String create(@RequestParam("transportId") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport = transportService.findById(id).get();
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

        /*Photo preview;
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
        model.addAttribute("ads", ads);*/
        model.addAttribute("ad", ad);
        return "ad/edit";
    }

    @PostMapping("ads/{id}/edit")
    public String update(@PathVariable("id") int adId, @RequestParam("transportId") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport = transportService.findById(id).get();
        ad.setTransport(transport);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        ad.setUser_id(userService.findUserByEmail(currentPrincipalName));
        ad.setTransport(transport);
        adRepository.save(ad);
        return "redirect:/lk/my_ads";
    }

    //удаление
    @PostMapping("ads/{id}/remove")
    public String delete(@PathVariable("id") int adId, @ModelAttribute("ad") Ad ad) {
        ad = adService.findById(adId).get();
        adRepository.delete(ad);
        return "redirect:/lk/my_ads";
    }

    // Filtered
    @GetMapping("/adsFiltered")
    public String getAdsFiltered(Model model) {
        model.addAttribute("ads", adService.findAll());
        return "ad/catalogAdsFiltered";
    }

}
