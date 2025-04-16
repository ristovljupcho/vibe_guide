package com.vibe_guide.exceptions;

import com.vibe_guide.enums.DayOfWeek;

import java.util.UUID;

public class WorkingHoursNotFoundException extends CustomNotFoundException {
    public WorkingHoursNotFoundException(UUID placeId, DayOfWeek dayOfWeek) {
        super("Place with id " + placeId + " and day of week " + dayOfWeek + " not found.");
    }

    public WorkingHoursNotFoundException(UUID placeId, String days) {
        super("Working days '" + days + "' for place with id " + placeId + " not found.");
    }
}
