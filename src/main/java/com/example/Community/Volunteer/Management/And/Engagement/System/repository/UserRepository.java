package com.example.Community.Volunteer.Management.And.Engagement.System.repository;

import com.example.Community.Volunteer.Management.And.Engagement.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmailId(String emailId);
}
