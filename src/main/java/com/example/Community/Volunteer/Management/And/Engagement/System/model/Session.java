package com.example.Community.Volunteer.Management.And.Engagement.System.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "user_sessions")
public class Session {
    @Id
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "email_id", nullable = false)
    private String emailId;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {createdAt = LocalDateTime.now();}
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
    public String getEmailId() {return emailId;}
    public void setEmailId(String emailId) {this.emailId = emailId;}
    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;
    }
}
