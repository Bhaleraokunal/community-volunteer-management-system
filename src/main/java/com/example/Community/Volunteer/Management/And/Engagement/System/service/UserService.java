package com.example.Community.Volunteer.Management.And.Engagement.System.service;

import com.example.Community.Volunteer.Management.And.Engagement.System.dto.UserLoginRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.UserRegisterRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.UserUpdateRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.User;

import java.util.Optional;

public interface UserService {
    String registerUser(UserRegisterRequest request);
    String loginUser(UserLoginRequest request);
    User updateUser(UserUpdateRequest request, String token);

    boolean updatePassword(String emailId,
            String oldPassword,
            String newPassword);

    Optional<User> getUserByEmail(String emailId);
    boolean logout(String token);

    // Add this method to get user by token
    Optional<User> getUserByToken(String token);
}

