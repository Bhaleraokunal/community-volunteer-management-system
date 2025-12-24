package com.example.Community.Volunteer.Management.And.Engagement.System.model;

import java.io.Serializable;
import java.util.Objects;

public class RegistrationId implements Serializable {

    private Long eventId;          // ✅ Long, NOT Integer
    private String volunteerId;

    public RegistrationId() {}

    public RegistrationId(Long eventId, String volunteerId) {
        this.eventId = eventId;
        this.volunteerId = volunteerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationId)) return false;
        RegistrationId that = (RegistrationId) o;
        return Objects.equals(eventId, that.eventId)
                && Objects.equals(volunteerId, that.volunteerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, volunteerId);
    }
}
