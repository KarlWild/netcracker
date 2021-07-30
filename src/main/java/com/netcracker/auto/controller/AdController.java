package com.netcracker.auto.controller;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Transport;
import com.netcracker.auto.repository.AdRepository;
import com.netcracker.auto.repository.TransportRepository;
import com.netcracker.auto.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Controller
public class AdController {

    private TransportService transportService;
    private AdRepository adRepository;

    @Autowired
    public AdController(AdRepository adRepository, TransportService transportService) {
        this.transportService = transportService;
        this.adRepository = adRepository;
    }

    @GetMapping("/ad/tmp")
    public String tmp() {
        return "ad/button";
    }

    //записывается в бд без transport_id
  /* @GetMapping("/new")
    public String newForm( @ModelAttribute("ad") Ad ad) {
        return "ad/form";
    }

    @PostMapping("/ad/tmp")
    public String create(@ModelAttribute("ad")  Ad ad) {
        adRepository.save(ad);
        return "redirect:tmp";
    }*/



///////////////////////////////////////////////////////////

    //не добавляется transport_id, не знаю как пофиксить
    @GetMapping("/new/{id}")
    public String newForm(@PathVariable("id") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport=transportService.findById(id).get();
        ad.setTransport(transport);
        //adRepository.save(ad);
        return "ad/form";
    }

    @PostMapping("/ad/tmp")
    public String create(@RequestParam("transportId") Integer id, @ModelAttribute("ad") Ad ad) {
        Transport transport=transportService.findById(id).get();
        ad.setTransport(transport);
        adRepository.save(ad);
        return "redirect:tmp";
    }

}















//  @GetMapping("/form/{id}")
//    public String getTransport(@PathVariable("id") Integer id, Model model){
//        Transport transport=transportService.findById(id).get();
//        model.addAttribute("transport", transport);
//        /*Ad ad=new Ad();
//        ad.setTransport(transport);
//        model.addAttribute("ad", ad);*/
//        return ("ad/form");
//    }

    /*@PostMapping("/transportsad")
    public String create(@ModelAttribute("ad") @Validated Ad ad,
                         BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors())
            return "ad/brands";
        try {
            adDAO.save(ad);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "redirect:ad/transports";
    }*/

   /* @PostMapping("/form")
    public String create(@ModelAttribute("ad") @Validated Ad ad,
                         BindingResult bindingResult) {
        //Ad ad = new Ad();
        adRepository.save(ad);
        return "redirect:ad/transports";
    }*/

    /*@PostMapping("/transportsad")
    public String create(@RequestParam Date yearOfIssue,
            @RequestParam String colour,
            @RequestParam Integer mileage,
            @RequestParam String stateNumber,
            @RequestParam String vin,
            @RequestParam String sts,
            @RequestParam Integer numberOfOwners,
            @RequestParam String address,
            @RequestParam String description,
            @RequestParam Long price,
            @RequestParam String driveUnit) {
        Ad ad = new Ad(yearOfIssue,colour,mileage,stateNumber,vin,sts,numberOfOwners,
                address, description,price,driveUnit);
        adRepository.save(ad);
        return "redirect:ad/transports";
    }*/



