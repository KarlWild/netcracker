package com.netcracker.auto.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
