package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Review;
import com.netcracker.auto.entity.RolesEntity;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.repository.ReviewRepository;
import com.netcracker.auto.service.AdService;
import com.netcracker.auto.service.FavouriteService;
import com.netcracker.auto.service.UserService;
import com.netcracker.auto.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * @author Anton Popkov
 */
@Controller
@RequestMapping("/lk")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private AdService adService;

    @GetMapping("/all")
    public String mainPage(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute( "user", user);
        return "pages/all";
    }

    @PostMapping("/all/update")
    public String saveDetails(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes,
                              Principal principal,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        User loggedUser = userService.findUserByEmail(principal.getName());
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(loggedUser.getEmail().concat(".jpg")));
            user.setImages(fileName);
            //String directory = ResourceUtils.getFile("classpath:static/user-photos/").getAbsolutePath();
            String uploadDir = "user-photos/";

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getImages().isEmpty())
                user.setImages(null);
        }
        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        else {
            user.setPassword(loggedUser.getPassword());
        }

        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "???????? ???????????? ??????????????????");
        return "redirect:/lk/all";
    }

    @PostMapping("/addBalance")
    public String addBalance(Integer money,
                             @ModelAttribute("user") User user) {
        Double finalMoney = money == null ? 0.0 : money;

        userService.updateBalance(finalMoney, user.getUserId());
        return "redirect:/lk/all";
    }

    /* Reviews */
    @GetMapping("/reviews")
    public String reviewsPage(Principal principal, Model model) {
        List<Review> reviewList = reviewRepository.findAllByUsername(principal.getName());
        User loggedInUser = userService.findUserByEmail(principal.getName());
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("user", loggedInUser);
        return "pages/reviews";
    }

    /**      User's ads      **/
    @Secured("ROLE_SELLER")
    @GetMapping("/my_ads")
    public String showAds(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("user", adService.findByUser(userService.findUserByEmail(currentPrincipalName)));
        User user = new User();
        return "ad/myAds";
    }

    //@{/lk/get/role_seller/{id}(id=${user.userId})}
    @GetMapping("/get/role_seller/{id}")
    public String buySellerRole(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByEmail(currentPrincipalName);
        if (user.getBalance() - 100 >= 0) user.setBalance(user.getBalance() - 100);
        user.addRole(RolesEntity.ROLE_SELLER);
        userService.saveUser(user);
        return "redirect:/lk/all";
    }

    //   User's favourites
    @GetMapping("/favourite")
    public String showFavourites(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("user", favouriteService.findFavourites(userService.findUserByEmail(currentPrincipalName)));
        return "ad/myFavourites";
    }

    @GetMapping("/map")
    public String showMap(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("user", favouriteService.findFavourites(userService.findUserByEmail(currentPrincipalName)));
        return "map/map2";
    }
}
