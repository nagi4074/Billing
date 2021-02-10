package com.example.billing.controllers;

import java.util.List;

import com.example.billing.domains.Application;
import com.example.billing.mappers.ApplicationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    private final ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationController(ApplicationMapper applicationMapper){
        this.applicationMapper = applicationMapper;
    }
    @GetMapping("/applicationhistory")
    public String index(Model model){
        List<Application> applications = applicationMapper.applicationAll();
        List<Application> totalBilling = applicationMapper.totalBilling();
        model.addAttribute("applications", applications);
        model.addAttribute("totalBilling", totalBilling);
        return "applicationhistory";
    }

    @GetMapping("/input")
    public String input(){
        return "input";
    }
}
