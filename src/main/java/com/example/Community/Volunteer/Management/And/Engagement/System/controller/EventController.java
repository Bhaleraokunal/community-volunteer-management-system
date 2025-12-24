package com.example.Community.Volunteer.Management.And.Engagement.System.controller;

import com.example.Community.Volunteer.Management.And.Engagement.System.dto.EventFeedbackRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.EventListResponse;
import com.example.Community.Volunteer.Management.And.Engagement.System.dto.EventRegisterRequest;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.Event_details;
import com.example.Community.Volunteer.Management.And.Engagement.System.model.User;
import com.example.Community.Volunteer.Management.And.Engagement.System.repository.EventRepository;
import com.example.Community.Volunteer.Management.And.Engagement.System.repository.UserRepository;
import com.example.Community.Volunteer.Management.And.Engagement.System.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    // 1️⃣ Create Event
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody Event_details event) {

        String organizerId = event.getOrganizerId();
        if (organizerId == null || organizerId.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", "organizerId is required"));
        }

        Optional<User> userOpt = userRepository.findById(organizerId);
        if (userOpt.isEmpty() ||
                !"ORGANIZER".equalsIgnoreCase(userOpt.get().getUserRole())) {
            return ResponseEntity.status(403)
                    .body(Map.of("status", "error", "message", "Only organizer can create event"));
        }

        Event_details saved = eventService.createEvent(event);

        return ResponseEntity.ok(
                Map.of("status", "created", "eventId", saved.getEventId())
        );
    }

    // 2️⃣ Update Event
    @PutMapping("/update")
    public ResponseEntity<?> updateEvent(@RequestBody Event_details event) {

        if (event.getEventId() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", "eventId is required"));
        }

        Event_details updated = eventService.updateEvent(event);
        if (updated == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("status", "error", "message", "Event not found"));
        }

        return ResponseEntity.ok(
                Map.of("status", "updated successfully", "eventId", updated.getEventId())
        );
    }

    // 3️⃣ Delete Event
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {

        boolean deleted = eventService.deleteEvent(eventId);
        if (!deleted) {
            return ResponseEntity.status(404)
                    .body(Map.of("status", "error", "message", "Event not found"));
        }

        return ResponseEntity.ok(Map.of("status", "deleted"));
    }

    // 4️⃣ Register Volunteer
    @PostMapping("/register")
    public ResponseEntity<?> registerVolunteer(@RequestBody EventRegisterRequest request) {

        Optional<Event_details> eventOpt =
                eventRepository.findById(request.getEventId());

        if (eventOpt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("status", "error", "message", "Event not found"));
        }

        Event_details event = eventOpt.get();

        if (!Boolean.TRUE.equals(event.getRegistrationAllowed())) {
            return ResponseEntity.ok(
                    Map.of("status", "error", "message", "Registration not allowed")
            );
        }

        if (event.getEndDate().isBefore(LocalDate.now())) {
            return ResponseEntity.ok(
                    Map.of("status", "error", "message", "Event already ended")
            );
        }

        String status = eventService.eventRegistration(
                request.getEventId(),
                request.getEmailId()
        );

        return ResponseEntity.ok(Map.of("status", status));
    }

//    // 5️⃣ Unregister Volunteer
    @PostMapping("/{eventId}/unregister")
    public ResponseEntity<?> unregisterVolunteer(
            @PathVariable Long eventId,
            @RequestBody EventRegisterRequest request) {

        String status = eventService.unregisterVolunteer(
                eventId,
                request.getEmailId()
        );

        return ResponseEntity.ok(Map.of("status", status));
    }



    // 6️⃣ List Events by Status
    @GetMapping("/list/{status}")
    public List<EventListResponse> listEvents(@PathVariable String status) {

        List<Event_details> events = eventService.listEventsByStatus(status);
        List<EventListResponse> response = new ArrayList<>();

        for (Event_details e : events) {
            EventListResponse dto = new EventListResponse();
            dto.setEventId(e.getEventId());
            dto.setName(e.getEventName());
            dto.setDescription(e.getDescription());
            dto.setAddress(e.getAddress());
            dto.setCity(e.getCity());
            dto.setStartDate(e.getStartDate());
            dto.setEndDate(e.getEndDate());
            dto.setVolunteersNeeded(e.getMaxAllowedRegistrations());
            dto.setRegistrationAllowed(e.getRegistrationAllowed());
            dto.setRating(e.getRating());
            response.add(dto);
        }

        return response;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable Long eventId) {

        Optional<Event_details> eventOpt = eventRepository.findById(eventId);

        if (eventOpt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "Event not found"));
        }

        return ResponseEntity.ok(eventOpt.get());
    }


    // 8️⃣ Event Registrations
    @GetMapping("/{eventId}/registrations")
    public ResponseEntity<?> registrations(@PathVariable Long eventId) {
        return ResponseEntity.ok(
                eventService.getEventRegistrations(eventId)
        );
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(
            @RequestBody EventRegisterRequest request) {

        String status = eventService.checkInVolunteer(
                request.getEventId(),
                request.getEmailId()
        );

        return ResponseEntity.ok(Map.of("status", status));
    }




    // 🔟 Participants
    @GetMapping("/{eventId}/participants")
    public ResponseEntity<?> participants(@PathVariable Long eventId) {
        return ResponseEntity.ok(
                eventService.getEventParticipants(eventId)
        );
    }

    // 1️⃣1️⃣ Feedback
    @PostMapping("/feedback")
    public ResponseEntity<?> feedback(@RequestBody EventFeedbackRequest request) {

        String result = eventService.submitFeedback(
                request.getEventId(),
                request.getEmailId(),
                request.getRating()
        );

        if ("success".equals(result)) {
            return ResponseEntity.ok(
                    Map.of("status", "success", "message", "Thanks for rating!")
            );
        }

        return ResponseEntity.ok(
                Map.of("status", result, "message", "Feedback not submitted")
        );
    }
}
