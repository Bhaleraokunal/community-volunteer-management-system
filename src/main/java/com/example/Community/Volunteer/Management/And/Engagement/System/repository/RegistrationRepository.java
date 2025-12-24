package com.example.Community.Volunteer.Management.And.Engagement.System.repository;
import java.util.Optional;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.RegistrationDetails;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.RegistrationId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository
extends JpaRepository<RegistrationDetails, RegistrationId> {

boolean existsByEventIdAndVolunteerId(Long eventId, String volunteerId);

@Modifying
@Transactional
void deleteByEventIdAndVolunteerId(Long eventId, String volunteerId);

long countByEventId(Long eventId);

@Query("SELECT r.volunteerId FROM RegistrationDetails r WHERE r.eventId = :eventId")
List<String> findVolunteerIdsByEventId(Long eventId);

Optional<RegistrationDetails>
findByEventIdAndVolunteerId(Long eventId, String volunteerId);

long countByEventIdAndCheckInTrue(Long eventId);

@Query("""
SELECT r.volunteerId
FROM RegistrationDetails r
WHERE r.eventId = :eventId AND r.checkIn = true
""")
List<String> findCheckedInVolunteerIdsByEventId(Long eventId);

@Query("SELECT AVG(r.rating) FROM RegistrationDetails r WHERE r.eventId = :eventId")
Double findAverageRatingByEventId(Long eventId);
}
