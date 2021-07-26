package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.User;
import com.netcracker.auto.security.MyUserDetails;
import com.netcracker.auto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Anton Popkov
 */
@Controller
@RequestMapping("/lk")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public String mainPage(Principal principal, Model model) {
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
}
