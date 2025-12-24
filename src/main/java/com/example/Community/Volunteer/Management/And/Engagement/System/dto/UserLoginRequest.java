package com.example.Community.Volunteer.Management.And.Engagement.System.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public class UserLoginRequest {
    @NotBlank(message = "Email ID is required")
    @Email(message = "Email should be valid")
    private String emailId;

    @NotBlank(message = "Password is required")
    private String password;
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
