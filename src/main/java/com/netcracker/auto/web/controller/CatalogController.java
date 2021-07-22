package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.Transport;
import com.netcracker.auto.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/catalog/cars")
public class CatalogController {

    private TransportService transportService;

    @Autowired
    public CatalogController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping
    public String showBrands(Model model) {
        List<String> brands = transportService.findDistinctBrand();
        model.addAttribute("brands", brands);
        return "catalog.cars/brands";
    }

    @GetMapping("/{brand}")
    public String showModels(@PathVariable String brand, Model model) {
        List<String> models = transportService.findDistinctModels(brand);
        model.addAttribute("brand", brand);
        model.addAttribute("models", models);
        return "catalog.cars/models";
    }

    @GetMapping("/{brand}/{model}")
    public String showGenerations(@PathVariable String brand, @PathVariable String model, Model m) {
        Map<String, String> generations = transportService.getDistinctGenerationsAndLinks(brand, model);
        m.addAttribute("brand", brand);
        m.addAttribute("model", model);
        m.addAttribute("generations", generations);
        return "catalog.cars/generation";
    }

    @GetMapping("/{brand}/{model}/{generation}")
    public String showCars(@PathVariable String brand,
                           @PathVariable String model,
                           @PathVariable String generation,
                           Model m) {
        List<Transport> transports = transportService.findByModelAndGeneration(model, generation);
        m.addAttribute("transports", transports);
        return "catalog.cars/transports";
    }
}