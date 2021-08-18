package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Review;
import com.netcracker.auto.entity.RolesEntity;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.repository.ReviewRepository;
import com.netcracker.auto.service.AdService;
import com.netcracker.auto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Anton Popkov
 */
@Controller
@RequestMapping("/lk")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdService adService;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/all")
    public String mainPage(Principal principal, Model model) throws Exception {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "pages/all";
    }

    @PostMapping("/all/update")
    public String saveDetails(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        return "redirect:/lk/all";
    }

    /**** Wallet *****/
    @GetMapping("/wallet")
    public String viewWallet(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "pages/wallet";
    }

    @PostMapping("/addBalance")
    public String addBalance(@RequestParam(value = "money") Double money,
                             @ModelAttribute("user") User user) {
        userService.updateBalance(money, user.getUserId());
        return "redirect:/lk/wallet";
    }

    /* Reviews */
    @GetMapping("/reviews")
    public String reviewsPage(Principal principal, Model model) {
        List<Review> reviewList = reviewRepository.findAllByUsername(principal.getName());
        Review review = reviewList.get(1);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("review", review);
        return "pages/reviews";
    }

    /**      User's ads      **/
    @GetMapping("/my_ads")
    public String showAds(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("user", adService.findByUser(userService.findUserByEmail(currentPrincipalName)));
        return "ad/myAds";
    }
    //@{/lk/get/role_seller/{id}(id=${user.userId})}
    @GetMapping("/get/role_seller/{id}")
    public String buySellerRole(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByEmail(currentPrincipalName);
        if(user.getBalance()-100>=0) user.setBalance(user.getBalance()-100);
        user.addRole(RolesEntity.ROLE_SELLER);
        userService.saveUser(user);
        return "redirect:/lk/all";
    }
}
