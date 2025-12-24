package com.example.Community.Volunteer.Management.And.Engagement.System.dto;

public class EventFeedbackRequest {

    private Long eventId;
    private String emailId;
    private Integer rating;

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
}
