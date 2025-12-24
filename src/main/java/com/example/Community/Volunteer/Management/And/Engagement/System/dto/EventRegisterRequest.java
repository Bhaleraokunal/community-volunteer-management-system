package com.example.Community.Volunteer.Management.And.Engagement.System.dto;

public class EventRegisterRequest {
    private Long eventId;
    private String emailId;

    // getters and setters
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
}

