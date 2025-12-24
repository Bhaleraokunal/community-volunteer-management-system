package com.example.Community.Volunteer.Management.And.Engagement.System.repository;

import com.example.Community.Volunteer.Management.And.Engagement.System.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

    Optional<Session> findByToken(String token);

    boolean existsByToken(String token);

    void deleteByToken(String token);
}
