package com.example.Community.Volunteer.Management.And.Engagement.System.repository;

import com.example.Community.Volunteer.Management.And.Engagement.System.model.Event_details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event_details, Long> {
    List<Event_details> findByStartDateGreaterThanEqual(LocalDate date);
    List<Event_details> findByEndDateLessThanEqual(LocalDate date);
    boolean existsByEventNameAndStartDateAndCity(
            String eventName,
            LocalDate startDate,
            String city
    );
}
