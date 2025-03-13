package com.vibe_guide.exceptions;

import java.util.UUID;

public class EventNotFoundException extends CustomNotFoundException {
    public EventNotFoundException(UUID eventId) {
        super(String.format("Event with id %s not found", eventId));
    }
}
