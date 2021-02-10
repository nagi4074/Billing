package com.example.billing.controllers;

import java.sql.Date;
import java.util.List;

import com.example.billing.domains.Application;
import com.example.billing.mappers.ApplicationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationController {
    private final ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationController(ApplicationMapper applicationMapper){
        this.applicationMapper = applicationMapper;
    }

    @InitBinder
    public void initBinde(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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
    public String showInputDataForm(){
        return "input";
    }

    @PostMapping("/input")
    public String input(@RequestParam(name = "date") String date, @RequestParam(name = "billing")Integer billing, @RequestParam(name = "appName")String application, @RequestParam(name = "result", defaultValue = "" )String result,@RequestParam(name = "remarks", defaultValue = "") String remarks){
        Date sqlDate = Date.valueOf(date);
        System.out.println(date);
        Application app = new Application(sqlDate, billing, application, result, remarks);
        applicationMapper.addData(app);
        return "applicationhistory";
    }
}
