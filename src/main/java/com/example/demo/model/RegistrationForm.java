package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
    
    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 10, message = "Location must be between 2 and 10 characters")
    private String location;
    
    private String invitedThrough;
    private String referencedBy;
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    
    public String getInvitedThrough() {
        return invitedThrough;
    }
    
    public void setInvitedThrough(String invitedThrough) {
        this.invitedThrough = invitedThrough;
    }
    
    public String getReferencedBy() {
        return referencedBy;
    }
    
    public void setReferencedBy(String referencedBy) {
        this.referencedBy = referencedBy;
    }
}