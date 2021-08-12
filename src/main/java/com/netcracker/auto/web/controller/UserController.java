package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Review;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.repository.ReviewRepository;
import com.netcracker.auto.service.UserService;
import com.netcracker.auto.util.FileUploadUtil;
import org.assertj.core.util.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
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

    @GetMapping("/all")
    public String mainPage(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "pages/all";
    }

    @PostMapping("/all/update")
    public String saveDetails(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes,
                              Principal principal,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        User loggedUser = userService.findUserByEmail(principal.getName());
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            loggedUser.setImages(fileName);
            //String directory = ResourceUtils.getFile("classpath:static/user-photos/").getAbsolutePath();
            String uploadDir = "user-photos/" + loggedUser.getUserId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (loggedUser.getImages().isEmpty())
                loggedUser.setImages(null);
        }
        if (!user.getPassword().isEmpty()) {
            loggedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userService.saveUser(loggedUser);
        //loggedUser.setEmail(principal.getName());
        redirectAttributes.addFlashAttribute("message", "Ваши данные обновлены");
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
        model.addAttribute("reviewList", reviewList);
        return "pages/reviews";
    }

    @GetMapping("/reviews/create")
    public String createReview(Principal principal, Review review, Model model) {
        model.addAttribute("rating", review);
        return "pages/create-review";
    }

    @PostMapping("/reviews/post")
    public String postReview(Principal principal,
                             @ModelAttribute("review") Review review) {
        //review.setGiver(principal.getName());
        reviewRepository.save(review);
        return "redirect:/lk/reviews";
    }

    /* Password */
    @GetMapping("/password")
    public String passwordPage(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "pages/change-password";
    }

    /*@PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("user") User user, Model model) {
        userService.chan();
        return "redirect:/lk/wallet";
    }*/

}
