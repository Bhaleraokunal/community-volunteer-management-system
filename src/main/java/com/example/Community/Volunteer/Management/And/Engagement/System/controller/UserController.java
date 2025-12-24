package com.example.Community.Volunteer.Management.And.Engagement.System.controller;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.UserUpdateRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.UserLoginRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.UserRegisterRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.UserResetPasswordRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.User;
import com.example.Community.Volunteer.Management.And.Engagement.System.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        try {
            String result = userService.registerUser(request);
            if ("User already exists!".equals(result)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginRequest request) {
        try {
            String response = userService.loginUser(request);
            if ("User not found!".equals(response) || "Invalid password!".equals(response)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(
            HttpServletRequest request,
            @Valid @RequestBody UserUpdateRequest updateRequest) {

        User authenticatedUser =
                (User) request.getAttribute("authenticatedUser");

        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }

        updateRequest.setEmailId(authenticatedUser.getEmailId());

        userService.updateUser(updateRequest, null);

        return ResponseEntity.ok("User updated successfully!");
    }


    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            HttpServletRequest httpRequest,
            @Valid @RequestBody UserResetPasswordRequest request) {

        User authenticatedUser =
                (User) httpRequest.getAttribute("authenticatedUser");

        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }

        boolean updated = userService.updatePassword(
                authenticatedUser.getEmailId(),   // ✅ from token
                request.getOldPassword(),         // ✅ validated
                request.getPassword()
        );

        if (!updated) {
            return ResponseEntity.badRequest()
                    .body("Old password is incorrect");
        }

        return ResponseEntity.ok("Password updated successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(HttpServletRequest request) {

        User authenticatedUser =
                (User) request.getAttribute("authenticatedUser");

        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || authHeader.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization header missing");
        }

        String token = authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : authHeader;

        boolean result = userService.logout(token);

        if (!result) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token or already logged out!");
        }

        return ResponseEntity.ok("User logged out successfully!");
    }



}
