package com.example.Community.Volunteer.Management.And.Engagement.System.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class CreateEventRequest {

    private String name;
    private String description;
    private String address;
    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Integer maximumAllowedRegistrations;
    private String organizer;
    private Boolean registrationAllowed;

    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getMaximumAllowedRegistrations() {
        return maximumAllowedRegistrations;
    }
    public void setMaximumAllowedRegistrations(Integer maximumAllowedRegistrations) {
        this.maximumAllowedRegistrations = maximumAllowedRegistrations;
    }

    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public Boolean getRegistrationAllowed() { return registrationAllowed; }
    public void setRegistrationAllowed(Boolean registrationAllowed) {
        this.registrationAllowed = registrationAllowed;
    }
}
