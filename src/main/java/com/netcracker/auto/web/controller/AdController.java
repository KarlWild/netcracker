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
    @Autowired
    private AdService adService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private ComparisonService comparisonService;


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
        if (principal != null)
            isAdInComparison = comparisonService.findComparisonByAdAndUser(ad, loggedInUser);
        else isAdInComparison = Optional.of(new ComparisonAds());

        model.addAttribute("user", loggedInUser);
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

//    @PostMapping("/ad/button")
//    public String tmp() {
//        return "ad/brands";
//    }

    @GetMapping("/new/{id}")
    public String newAd(@PathVariable("id") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport = transportService.findById(id).get();
        ad.setTransport(transport);
        return "ad/form";
    }

    @PostMapping("/ad/create")
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

        List<Ad> ads = new ArrayList<>();

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
    public String deleteFavourite(@PathVariable("id") int adId, @ModelAttribute("favourite") Favourite f) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        f=favouriteRepository.findFavourite(userService.findUserByEmail(currentPrincipalName), adService.findById(adId).get());
        favouriteRepository.delete(f);
        return "redirect:/lk/favourite";
    }

    //отправка на модерацию
    @PostMapping("ads/{id}/check")
    public String check(@PathVariable("id") Integer adId) {
        Ad ad = adService.findById(adId).get();
        ad.setStatus("На модерации");
        adRepository.save(ad);
        return "redirect:/ads/{id}";
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
