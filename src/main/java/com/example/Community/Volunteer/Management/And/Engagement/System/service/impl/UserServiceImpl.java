package com.example.Community.Volunteer.Management.And.Engagement.System.service.impl;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.*;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.User;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.Session;
import com.example.Community.Volunteer.Management.And.Engagement.System.repository.UserRepository;
import com.example.Community.Volunteer.Management.And.Engagement.System.repository.SessionRepository;
import com.example.Community.Volunteer.Management.And.Engagement.System.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    // ================== REGISTER ==================
    @Override
    public String registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmailId(request.getEmailId())) {
            return "User already exists!";
        }
        if (!request.getUserRole().equalsIgnoreCase("VOLUNTEER")
                && !request.getUserRole().equalsIgnoreCase("ORGANIZER")) {
            throw new IllegalArgumentException("Invalid role! Role must be VOLUNTEER or ORGANIZER.");
        }
        User user = new User();
        user.setEmailId(request.getEmailId());
        user.setPhoneNo(request.getPhoneNo());
        user.setAddress(request.getAddress());
        user.setPassword(request.getPassword());
        user.setUserRole(request.getUserRole());
        userRepository.save(user);
        return "Register Successfully";
    }


    // ================== LOGIN ==================
    @Override
    public String loginUser(UserLoginRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getEmailId());
        if (optionalUser.isEmpty()) {
            return "User not found!";
        }
        User user = optionalUser.get();
        if (!user.getPassword().equals(request.getPassword())) {
            return "Invalid password!";
        }
        // Generate token
        String token = UUID.randomUUID().toString();
        // Save token in session table
        Session session = new Session();
        session.setToken(token);
        session.setEmailId(user.getEmailId());
        sessionRepository.save(session);
        return "Login Successful! Token: " + token;
    }


    // ================== UPDATE PROFILE ==================
    @Override
    public User updateUser(UserUpdateRequest request, String token) {

        // ❌ REMOVE session validation completely

        Optional<User> optionalUser = userRepository.findById(request.getEmailId());
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User not found!");
        }

        User user = optionalUser.get();

        if (request.getPhoneNo() != null)
            user.setPhoneNo(request.getPhoneNo());

        if (request.getAddress() != null)
            user.setAddress(request.getAddress());

        if (request.getUserRole() != null) {
            if (!request.getUserRole().equalsIgnoreCase("VOLUNTEER")
                    && !request.getUserRole().equalsIgnoreCase("ORGANIZER")) {
                throw new IllegalArgumentException("Invalid role! Must be VOLUNTEER or ORGANIZER.");
            }
            user.setUserRole(request.getUserRole());
        }

        return userRepository.save(user);
    }



    // ================== RESET PASSWORD ==================
    @Override
    public boolean updatePassword(
            String emailId,
            String oldPassword,
            String newPassword) {

        Optional<User> optionalUser = userRepository.findById(emailId);
        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        // 🔐 Old password verification
        if (!user.getPassword().equals(oldPassword)) {
            return false;
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> getUserByEmail(String emailId) {
        return userRepository.findById(emailId);
    }


    // ================== LOGOUT ==================
    @Transactional
    @Override
    public boolean logout(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }

        if (!sessionRepository.existsByToken(token)) {
            return false;
        }

        sessionRepository.deleteByToken(token);
        return true;
    }

    @Override
    public Optional<User> getUserByToken(String token) {
        Optional<Session> sessionOpt = sessionRepository.findByToken(token);
        if (sessionOpt.isEmpty()) {
            return Optional.empty();
        }

        String emailId = sessionOpt.get().getEmailId();
        return userRepository.findById(emailId);
    }
    
    


}
