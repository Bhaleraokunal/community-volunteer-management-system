package com.example.Community.Volunteer.Management.And.Engagement.System.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
@Entity
@Table(name = "user_details")
public class User {
    @Id
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "phone_no")
    private String phoneNo;
    private String address;
    @JsonIgnore
    private String password;
    @Column(name = "user_role")
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
