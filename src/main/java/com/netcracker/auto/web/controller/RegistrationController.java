package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.User;
import com.netcracker.auto.service.IUserService;
import com.netcracker.auto.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public class RegistrationController {
    @Autowired
    private IUserService userService;

    @GetMapping("/reg")
    public String registration(WebRequest request, Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "pages/reg";
    }

    @PostMapping("/reg?action=registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDto,
            HttpServletRequest request,
            Errors errors) {

        //try {
            User registered = userService.registerNewUserAccount(userDto);
        //} catch (UserAlreadyExistException uaeEx) {
        //    mav.addObject("message", "An account for that username/email already exists.");
        //    return mav;
        //}

        return new ModelAndView("successRegister", "user", userDto);
    }
}
