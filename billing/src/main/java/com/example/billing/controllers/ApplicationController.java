package com.example.billing.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.example.billing.domains.Application;
import com.example.billing.mappers.ApplicationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //apllicationhistory.html
    @GetMapping("/applicationhistory")
    public String index(Model model){
        List<Application> applications = applicationMapper.applicationAll();
        Application totalBilling = applicationMapper.totalBilling();
        model.addAttribute("applications", applications);
        model.addAttribute("totalBilling", totalBilling);
        return "applicationhistory";
    }

    //input.html
    @GetMapping("/input")
    public String showInputDataForm(){
        return "input";
    }

    @PostMapping("/input")
    public String input(@RequestParam(name = "date") String date, @RequestParam(name = "billing")Integer billing, @RequestParam(name = "appName")String application, @RequestParam(name = "result", defaultValue = "" )String result,@RequestParam(name = "remarks", defaultValue = "") String remarks){
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        Application app = new Application(sqlDate, billing, application, result, remarks);
        applicationMapper.addData(app);
        return "redirect:/result";
    }

    //result.html
    @GetMapping("/result")
    public String result(Model model){
        Application resultData = applicationMapper.showResultData();
        Application totalBilling = applicationMapper.totalBilling();
        Application billingOfApp = applicationMapper.showTotalBillingOfApp();

        java.util.Date inputDate = resultData.getDate();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String date = f.format(inputDate);
        String applicationName = resultData.getApplication();
        int billing = resultData.getBilling();
        String result = resultData.getResult();
        String remarks = resultData.getRemarks();
        int total = totalBilling.getBilling();
        int totalInApp = billingOfApp.getBilling();
        

        model.addAttribute("appName", applicationName);
        model.addAttribute("date", date);
        model.addAttribute("billing", billing);
        model.addAttribute("result", result);
        model.addAttribute("remarks", remarks);

        model.addAttribute("total", total);
        model.addAttribute("totalInApp", totalInApp);
        return "result";
    }

    //billinghistory.html
    @GetMapping("/billinghistory/{appName}")
    public String billingHistory(@PathVariable("appName") String appName, Model model){
        Application billingInApp = applicationMapper.showTotalBillingInApp(appName);
        List<Application> resultData = applicationMapper.showAllDataInApp(appName);

        int total = billingInApp.getBilling();
        List<Map<String, Object>> allData = new ArrayList<Map<String,Object>>();

        for(Application app : resultData){
            Map<String, Object> data = new HashMap<String, Object>();

            Date date = app.getDate();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

            data.put("id", app.getId());
            data.put("date", f.format(date));
            data.put("billing", app.getBilling());
            data.put("application", app.getApplication());
            data.put("result", app.getResult());
            data.put("remarks", app.getRemarks());

            allData.add(data);
        }

        model.addAttribute("total", total);
        model.addAttribute("allData", allData);

        return "billinghistory";
    }

    /*
    @DeleteMapping("/billinghistory/{appName}")
    public void deleteData(){
        applicationMapper.delete();
    }*/
    


    //edit.html
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model){
        Application data = applicationMapper.showOneData(id);

        String[] assesment = {"普通", "当たり", "爆死"};
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String date = f.format(data.getDate());

        model.addAttribute("data", data);
        model.addAttribute("date", date);
        model.addAttribute("assesment", assesment);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, @RequestParam(name = "date") String date, @RequestParam(name = "billing")Integer billing, @RequestParam(name = "appName")String application, @RequestParam(name = "result", defaultValue = "" )String result,@RequestParam(name = "remarks", defaultValue = "") String remarks, RedirectAttributes attributes){
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        Application app = new Application(id, sqlDate, billing, application, result, remarks);
        applicationMapper.update(app);

        attributes.addAttribute("url", application);

        return "redirect:/billinghistory/{url}";
    }

    
}
