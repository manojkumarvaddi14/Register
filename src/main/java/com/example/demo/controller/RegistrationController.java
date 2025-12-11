package com.example.demo.controller;

import com.example.demo.model.RegistrationForm;
import com.example.demo.service.GoogleSheetsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {
    
    @Autowired
    private GoogleSheetsService googleSheetsService;
    
    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }
    
    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute RegistrationForm form,
                           BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        
        try {
            // Save to Google Sheets
            boolean success = googleSheetsService.saveToGoogleSheets(form);
            
            if (success) {
                model.addAttribute("success", true);
                model.addAttribute("message", "✅ Registration successful! Data saved to Google Sheets.");
            } else {
                model.addAttribute("error", true);
                model.addAttribute("message", "❌ Failed to save data. Please try again.");
            }
            
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "❌ Error: " + e.getMessage());
        }
        
        // Return fresh form
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }
    
    @GetMapping("/test-sheets")
    @ResponseBody
    public String testSheetsConnection() {
        boolean connected = googleSheetsService.testConnection();
        return connected ? "✅ Google Sheets connection successful!" 
                        : "❌ Google Sheets connection failed!";
    }
}