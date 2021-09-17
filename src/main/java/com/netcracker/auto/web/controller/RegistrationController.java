package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.User;
import com.netcracker.auto.service.UserService;
import com.netcracker.auto.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(
            value = "/reg",
            method = {RequestMethod.POST})
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDto,
            HttpServletRequest request,
            Errors errors) {
        //userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        //try {
        User registered = userService.registerNewUserAccount(userDto);
        //} catch (UserAlreadyExistException uaeEx) {
        //    mav.addObject("message", "An account for that username/email already exists.");
        //    return mav;
        //}

        return new ModelAndView("pages/login", "user", userDto);
    }

}
