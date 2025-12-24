package com.example.Community.Volunteer.Management.And.Engagement.System.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class UserRegisterRequest {
    @NotBlank(message = "Email ID is required")
    @Email(message = "Email should be valid")
    private String emailId;
    @NotBlank(message = "Phone number is required")
    private String phoneNo;
    private String address;
    @NotBlank(message = "Password is required")
    private String password;
    @NotNull(message = "User role is required")
    private String userRole;
    public String getEmailId() {return emailId;}
    public void setEmailId(String emailId) {this.emailId = emailId;}
    public String getPhoneNo() {return phoneNo;}
    public void setPhoneNo(String phoneNo) {this.phoneNo = phoneNo;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getUserRole() {return userRole;}
    public void setUserRole(String userRole) {this.userRole = userRole;}
}
