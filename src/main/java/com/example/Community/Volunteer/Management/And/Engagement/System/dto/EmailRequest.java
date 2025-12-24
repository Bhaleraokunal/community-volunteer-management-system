package com.example.Community.Volunteer.Management.And.Engagement.System.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String emailId;
    public String getEmailId() {
        return emailId;
    }
}
