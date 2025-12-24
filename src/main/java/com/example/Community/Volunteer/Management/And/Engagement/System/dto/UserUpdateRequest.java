package com.example.Community.Volunteer.Management.And.Engagement.System.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateRequest {
    @NotBlank(message = "Email ID is required")
    @Email(message = "Email should be valid")
    private String emailId;
    private String phoneNo;
    private String address;
    private String userRole;
    public String getEmailId() {return emailId;}
    public void setEmailId(String emailId) {this.emailId = emailId;}
    public String getPhoneNo() {return phoneNo;}
    public void setPhoneNo(String phoneNo) {this.phoneNo = phoneNo;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public String getUserRole() {return userRole;}
    public void setUserRole(String userRole) {this.userRole = userRole;}
}
