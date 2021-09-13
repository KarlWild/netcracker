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
import java.util.Optional;
import java.util.function.Predicate;

@Controller
public class AdController {

    private AdService adService;
    private PhotoService photoService;
    private TransportService transportService;
    private AdRepository adRepository;
    private UserService userService;
    private FavouriteRepository favouriteRepository;
    private ReviewService reviewService;
    private FavouriteService favouriteService;

    @Autowired
    private ComparisonService comparisonService;

    @Autowired
    public AdController(FavouriteService favouriteService, AdService adService, PhotoService photoService, TransportService transportService,
                        AdRepository adRepository, UserService userService, FavouriteRepository favouriteRepository, ReviewService reviewService) {
        this.userService = userService;
        this.adService = adService;
        this.photoService = photoService;
        this.transportService = transportService;
        this.adRepository = adRepository;
        this.favouriteRepository = favouriteRepository;
        this.reviewService = reviewService;
        this.favouriteService = favouriteService;
    }

    @GetMapping("ads/{id}")
    public String getAd(@ModelAttribute("user") User user,
                        Principal principal, @PathVariable("id") int id, Model model, @ModelAttribute("favourite") Favourite favourite) {
        Ad ad = adService.findById(id).get();
        User loggedInUser;
        if (principal != null)
            loggedInUser = userService.findUserByEmail(principal.getName());
        else {
            loggedInUser = new User();
            loggedInUser.setEmail("");
        }
        Optional<ComparisonAds> isAdInComparison;
        if(principal !=null)
         isAdInComparison = comparisonService.findComparisonByAdAndUser(ad, loggedInUser);
        else isAdInComparison = Optional.of(new ComparisonAds());
        List<Photo> photos = new ArrayList<>();

        if (ad.getPhotos().isEmpty()) {
            photos.add(photoService.getNoPhoto());
        } else {
            photos = ad.getPhotos();
        }

        model.addAttribute("user", loggedInUser);
        model.addAttribute("photos", photos);
        model.addAttribute("ad", ad);
        model.addAttribute("comparison", isAdInComparison);

        Review review = new Review(ad.getUser_id().getEmail(),
                " ", 0, loggedInUser.getEmail());
        model.addAttribute("review", review);

        return "ad/ad";
    }

    @PostMapping("/ads/post-review")
    public String postReview(@ModelAttribute("review") Review review) {
        reviewService.saveReview(review);

        User user = userService.findUserByEmail(review.getUsername());
        Double seller_rating = reviewService.calculateSellerRatingByUsername(user.getEmail());
        user.setSeller_rating(seller_rating);
        userService.saveUser(user);

        return "redirect:/ads";
    }

    @GetMapping("/ads")
    public String getAds(Model model, String brand, String carModel,
                         Integer yearStart, Integer yearEnd,
                         Integer priceStart, Integer priceEnd,
                         Integer mileageStart, Integer mileageEnd,
                         Integer ownersCount) {
        List<Ad> ads = adService.findByAnyCriteria(brand, carModel,
                yearStart, yearEnd, priceStart, priceEnd, mileageStart, mileageEnd, ownersCount);
        Predicate<Ad> isQualified = ad -> !ad.isVerified();
        ads.removeIf(isQualified);
        //ads.stream().filter(isQualified);
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
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        ad.setUser_id(userService.findUserByEmail(currentPrincipalName));

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
        //adService.updateAd(adId, ad);
        adRepository.save(ad);
        return "redirect:/lk/my_ads";
    }

    //удаление
    @PostMapping("ads/{id}/remove")
    public String delete(@PathVariable("id") Integer adId) {
        Ad ad = adService.findById(adId).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (ad.getUser_id() == userService.findUserByEmail(currentPrincipalName))
            adRepository.delete(ad);
        return "redirect:/lk/my_ads";
    }

    @PostMapping("ads/{id}/favourite")
    public String addFavourite(@PathVariable("id") int adId, @ModelAttribute("favourite") Favourite f) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        f.setUser_id(userService.findUserByEmail(currentPrincipalName));
        f.setAd(adService.findById(adId).get());
        favouriteRepository.save(f);
        return "redirect:/lk/my_ads";
    }

    //убрать из избранного
    @PostMapping("ads/{id}/notFavourite")
    public String deleteFavourite(@PathVariable("id") int adId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Favourite f = favouriteRepository.findFavourite(userService.findUserByEmail(currentPrincipalName), adService.findById(adId).get());
        favouriteRepository.delete(f);
        return "redirect:/lk/favourite";
    }

    //отправка на модерацию
    @PostMapping("ads/{id}/check")
    public String check(@PathVariable("id") Integer adId) {
        Ad ad = adService.findById(adId).get();
        ad.setStatus("На модерации");
        adRepository.save(ad);
        return "redirect:/lk/my_ads";
    }

    //продано
    @PostMapping("ads/{id}/sold")
    public String sold(@PathVariable("id") Integer adId) {
        Ad ad = adService.findById(adId).get();
        ad.setStatus(" продано ");
        adRepository.save(ad);
        return "redirect:/lk/my_ads";
    }

    /*@PatchMapping("/ads/{id}/edit")
        public String change(@ModelAttribute("ad") Ad ad, @PathVariable("id") int adId, @RequestParam("transportId") int id){
            ad=adService.findById(adId).get();

            return "redirect:/lk/my_ads";
        }*/

    @GetMapping("/api/get_addresses")
    public @ResponseBody
    List<Object[]> getAllAddresses() {
        return adService.findAllAddresses();
    }
}
