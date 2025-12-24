package com.example.Community.Volunteer.Management.And.Engagement.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registration_details")
@IdClass(RegistrationId.class)
public class RegistrationDetails {

	@Id
	@Column(name = "event_id")
	private Long eventId;


    @Id
    @Column(name = "volunteer_id")
    private String volunteerId;

    @Column(nullable = false)
    private String status;

    @Column(name = "check_in")
    private Boolean checkIn;

    private Integer rating;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    // Getters and setters
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getVolunteerId() { return volunteerId; }
    public void setVolunteerId(String volunteerId) { this.volunteerId = volunteerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getCheckIn() { return checkIn; }
    public void setCheckIn(Boolean checkIn) { this.checkIn = checkIn; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getModifiedAt() { return modifiedAt; }
    public void setModifiedAt(LocalDateTime modifiedAt) { this.modifiedAt = modifiedAt; }
}
