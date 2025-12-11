package com.example.demo.service;

import com.example.demo.model.RegistrationForm;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleSheetsService {
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RestTemplate restTemplate = new RestTemplate();
    
    // REPLACE THIS WITH YOUR ACTUAL WEB APP URL
    private final String WEB_APP_URL = "https://docs.google.com/spreadsheets/d/1o3ScHdOo3nDQ6bdO0QP2Sx9izIu8lhXCnPP1VSSGnuI/";
    
    public boolean saveToGoogleSheets(RegistrationForm form) {
        try {
            System.out.println("📝 Saving to Google Sheets: " + form.getName());
            System.out.println("📱 Phone: " + form.getPhoneNumber());
            System.out.println("📍 Location: " + form.getLocation());
            System.out.println("📢 Invited: " + form.getInvitedThrough());
            System.out.println("👥 Referral: " + form.getReferencedBy());
            
            // Build URL with CORRECT parameter names
            String url = buildUrl(form);
            System.out.println("🔗 Calling URL: " + url);
            
            // Make GET request
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            System.out.println("✅ Response: " + response.getBody());
            System.out.println("📊 Status: " + response.getStatusCode());
            
            return response.getStatusCode().is2xxSuccessful();
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private String buildUrl(RegistrationForm form) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("name", encode(form.getName()));
            params.put("phone", encode(form.getPhoneNumber()));
            params.put("location", encode(form.getLocation())); // City/Town
            params.put("invited", encode(form.getInvitedThrough()));
            params.put("referral", encode(form.getReferencedBy()));
            params.put("date", encode(LocalDateTime.now().format(formatter)));
            
            StringBuilder urlBuilder = new StringBuilder(WEB_APP_URL + "?");
            boolean first = true;
            
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!first) {
                    urlBuilder.append("&");
                }
                urlBuilder.append(entry.getKey())
                         .append("=")
                         .append(entry.getValue());
                first = false;
            }
            
            return urlBuilder.toString();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to build URL", e);
        }
    }
    
    private String encode(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "";
        }
        return URLEncoder.encode(value.trim(), StandardCharsets.UTF_8);
    }
 // Add this method to your GoogleSheetsService for debugging
    public void debugFormData(RegistrationForm form) {
        System.out.println("=== FORM DATA DEBUG ===");
        System.out.println("Name: '" + form.getName() + "'");
        System.out.println("Phone: '" + form.getPhoneNumber() + "'");
        System.out.println("Location: '" + form.getLocation() + "'");
        System.out.println("Invited Through: '" + form.getInvitedThrough() + "'");
        System.out.println("Referenced By: '" + form.getReferencedBy() + "'");
        System.out.println("=== END DEBUG ===");
    }
}