package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.ComparisonAds;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.service.AdService;
import com.netcracker.auto.service.ComparisonService;
import com.netcracker.auto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Anton Popkov
 */
@Controller
public class ComparisonController {

    private final ComparisonService comparisonService;
    private final UserService userService;
    private final AdService adService;

    @Autowired
    public ComparisonController(ComparisonService comparisonService, UserService userService,
                                AdService adService) {
        this.comparisonService = comparisonService;
        this.userService = userService;
        this.adService = adService;
    }

    @GetMapping("/comparison")
    public String showComparison(Model model,
                                 Principal principal) {
        User loggedInUser = userService.findUserByEmail(principal.getName());
        List<ComparisonAds> adsList = comparisonService.listComparisonAds(loggedInUser);

        model.addAttribute("ads", adsList);

        return "pages/comparison";
    }

    @PostMapping("/ads/{id}/addComparison")
    public String addOrDeleteComparison(@PathVariable("id") Integer id, Principal principal) {
        Ad ad = adService.findById(id).orElse(null);

        User loggedInUser = userService.findUserByEmail(principal.getName());
        ComparisonAds comparisonAd = new ComparisonAds(ad, loggedInUser);
        List<ComparisonAds> adsList = comparisonService.listComparisonAds(loggedInUser);

        if (adsList.size() < 5) {
            if (comparisonService.findComparisonByAdAndUser(ad, loggedInUser).isEmpty())
                comparisonService.saveAd(comparisonAd);
            else comparisonService.deleteComparisonByAdAndUser(ad, loggedInUser);
        } else comparisonService.deleteComparisonByAdAndUser(ad, loggedInUser);

        return "redirect:/ads/" + id;
    }

    @ResponseBody
    @GetMapping("ads/{id}/getComparison")
    public Boolean getComparison(@PathVariable int id, Principal principal) {
        Ad ad = adService.findById(id).orElse(null);
        User loggedInUser = userService.findUserByEmail(principal.getName());
        List<ComparisonAds> adsList = comparisonService.listComparisonAds(loggedInUser);

        Boolean flag = Boolean.FALSE;
        if (adsList.size() <= 5) {
            if (comparisonService.findComparisonByAdAndUser(ad, loggedInUser).isPresent())
                flag = Boolean.TRUE;
        }

        return flag;
    }
}
