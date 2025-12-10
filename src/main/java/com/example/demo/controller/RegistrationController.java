package com.example.demo.controller;

import com.example.demo.model.RegistrationForm;
import com.example.demo.service.ExcelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    
    @Autowired
    private ExcelService excelService;
    
    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        System.out.println("GET / called - showing form");
        return "registration";
    }
    
    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                           BindingResult bindingResult,
                           Model model) {
        System.out.println("POST /register called");
        
        if (bindingResult.hasErrors()) {
            System.out.println("Form has errors: " + bindingResult.getAllErrors());
            return "registration";
        }
        
        try {
            excelService.saveToExcel(registrationForm);
            System.out.println("Data saved to Excel successfully");
            model.addAttribute("success", true);
            model.addAttribute("message", "Registration successful! Data saved to Excel.");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", true);
            model.addAttribute("message", "Error saving data: " + e.getMessage());
        }
        
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }
}