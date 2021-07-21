package com.netcracker.auto.web.controller;

import com.netcracker.auto.web.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Anton Popkov
 */
@Controller
@RequestMapping("/")
public class PagesController {
    @GetMapping
    public String home() {
        return "pages/index";
    }

    @GetMapping("/login")
    public String login(){
        return "pages/login";
    }

    @GetMapping("/reg")
    public String registration(WebRequest request, Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "pages/reg";
    }

}
