package com.example.Community.Volunteer.Management.And.Engagement.System.service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.Event_details;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.RegistrationDetails;
import com.example.Community.Volunteer.Management.And.Engagement.System.repository.EventRepository;
import com.example.Community.Volunteer.Management.And.Engagement.System.repository.RegistrationRepository;
import com.example.Community.Volunteer.Management.And.Engagement.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    // 1. Create Event
    public Event_details createEvent(Event_details event) {

        boolean exists = eventRepository.existsByEventNameAndStartDateAndCity(
                event.getEventName(),
                event.getStartDate(),
                event.getCity()
        );

        if (exists) {
            throw new RuntimeException("Duplicate event already exists");
        }

        return eventRepository.save(event);
    }

    // 2. Update Event
    public Event_details updateEvent(Event_details event) {

        Optional<Event_details> existing =
                eventRepository.findById(event.getEventId()); // Long

        if (existing.isPresent()) {
            Event_details e = existing.get();
            e.setEventName(event.getEventName());
            e.setAddress(event.getAddress());
            e.setCity(event.getCity());
            e.setStartDate(event.getStartDate());
            e.setEndDate(event.getEndDate());
            e.setMaxAllowedRegistrations(event.getMaxAllowedRegistrations());
            e.setRegistrationAllowed(event.getRegistrationAllowed());
            return eventRepository.save(e);
        }

        return null;
    }

    // 3. Delete Event
    public boolean deleteEvent(Long eventId) {

        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return true;
        }

        return false;
    }

    // 4. Register Volunteer
    public String eventRegistration(Long eventId, String volunteerEmail) {

        if (!userRepository.existsById(volunteerEmail)) {
            return "volunteer_not_found";
        }

        if (registrationRepository.existsByEventIdAndVolunteerId(eventId, volunteerEmail)) {
            return "already_registered";
        }

        RegistrationDetails reg = new RegistrationDetails();
        reg.setEventId(eventId);               // Long
        reg.setVolunteerId(volunteerEmail);
        reg.setStatus("REGISTERED");
        reg.setCheckIn(false);
        reg.setCreatedAt(LocalDateTime.now());
        reg.setModifiedAt(LocalDateTime.now());

        registrationRepository.save(reg);

        return "registered";
    }

    // 5. Unregister Volunteer
    @Transactional
    public String unregisterVolunteer(Long eventId, String volunteerEmail) {

        if (!registrationRepository.existsByEventIdAndVolunteerId(eventId, volunteerEmail)) {
            return "not_found";
        }

        registrationRepository.deleteByEventIdAndVolunteerId(eventId, volunteerEmail);
        return "un-registered";
    }

    // 6. List Events
    public List<Event_details> listEventsByStatus(String status) {

        if ("UPCOMING".equalsIgnoreCase(status)) {
            return eventRepository.findByStartDateGreaterThanEqual(LocalDate.now());
        }

        if ("PAST".equalsIgnoreCase(status)) {
            return eventRepository.findByEndDateLessThanEqual(LocalDate.now());
        }

        return eventRepository.findAll();
    }

    // 7. Event Registrations
    public Map<String, Object> getEventRegistrations(Long eventId) {

        long total = registrationRepository.countByEventId(eventId);
        List<String> volunteers =
                registrationRepository.findVolunteerIdsByEventId(eventId);

        Map<String, Object> response = new HashMap<>();
        response.put("totalRegistrations", total);
        response.put("volunteers", volunteers);

        return response;
    }

    // 8. Check-in Volunteer
    @Transactional
    public String checkInVolunteer(Long eventId, String emailId) {

        Optional<RegistrationDetails> regOpt =
                registrationRepository.findByEventIdAndVolunteerId(eventId, emailId);

        if (regOpt.isEmpty()) {
            return "not_registered";
        }

        RegistrationDetails reg = regOpt.get();

        if (Boolean.TRUE.equals(reg.getCheckIn())) {
            return "already_checked_in";
        }

        reg.setCheckIn(true);
        reg.setModifiedAt(LocalDateTime.now());

        registrationRepository.save(reg);

        return "check in successful";
    }

    // 9. Participants
    public Map<String, Object> getEventParticipants(Long eventId) {

        long totalParticipants =
                registrationRepository.countByEventIdAndCheckInTrue(eventId);

        List<String> volunteers =
                registrationRepository.findCheckedInVolunteerIdsByEventId(eventId);

        Map<String, Object> response = new HashMap<>();
        response.put("totalParticipants", totalParticipants);
        response.put("volunteers", volunteers);

        return response;
    }

    // 10. Feedback
    @Transactional
    public String submitFeedback(Long eventId, String emailId, Integer rating) {

        if (rating == null || rating < 1 || rating > 5) {
            return "invalid_rating";
        }

        Optional<RegistrationDetails> regOpt =
                registrationRepository.findByEventIdAndVolunteerId(eventId, emailId);

        if (regOpt.isEmpty()) {
            return "not_registered";
        }

        RegistrationDetails reg = regOpt.get();

        if (!Boolean.TRUE.equals(reg.getCheckIn())) {
            return "not_checked_in";
        }

        reg.setRating(rating);
        reg.setModifiedAt(LocalDateTime.now());
        registrationRepository.save(reg);

        Double avgRating =
                registrationRepository.findAverageRatingByEventId(eventId);

        if (avgRating != null) {
            eventRepository.findById(eventId).ifPresent(event -> {
                event.setRating(avgRating.floatValue());
                eventRepository.save(event);
            });
        }

        return "success";
    }
}
