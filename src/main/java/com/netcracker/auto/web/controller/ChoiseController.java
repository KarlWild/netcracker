package com.netcracker.auto.web.controller;

//import com.netcracker.auto.DAO.AdDAO;
import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Transport;
import com.netcracker.auto.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ad")
public class ChoiseController {

    private TransportService transportService;

    @Autowired
    public ChoiseController(TransportService transportService) {
        this.transportService = transportService;
    }

    //временный адрес для перехода к заполнению/просмотру объявления
    /*@GetMapping("/tmp")
    public String tmp(){
        return "ad/button";
    }*/

    @GetMapping("/brands")
    public String showBrands(Model model) {
        List<String> brands = transportService.findDistinctBrand();
        model.addAttribute("brands", brands);
        return "ad/brands";
    }

    @GetMapping("/{brand}")
    public String showModels(@PathVariable String brand, Model model) {
        List<String> models = transportService.findDistinctModels(brand);
        model.addAttribute("brand", brand);
        model.addAttribute("models", models);
        return "ad/models";
    }

    @GetMapping("/{brand}/{model}")
    public String showGenerations(@PathVariable String brand, @PathVariable String model, Model m) {
        Map<String, String> generations = transportService.getDistinctGenerationsAndLinks(brand, model);
        m.addAttribute("brand", brand);
        m.addAttribute("model", model);
        m.addAttribute("generations", generations);
        return "ad/generation";
    }

    //@RequestParam ("")
    @GetMapping("/{brand}/{model}/{generation}")
    public String showCars(@PathVariable String brand,
                           @PathVariable String model,
                           @PathVariable String generation,
                           Model m, @ModelAttribute Ad ad) {
        List<Transport> transports = transportService.findByModelAndGeneration(model, generation);
        m.addAttribute("transports", transports);
        return "ad/transports";
    }

    //просмотр своих объявлений
    @GetMapping("/myAds")
    public String showAds(){
        return "ad/ads";
    }

}