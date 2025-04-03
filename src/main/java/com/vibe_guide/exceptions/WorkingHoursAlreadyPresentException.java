package com.vibe_guide.exceptions;

import com.vibe_guide.enums.DayOfWeek;

import java.util.List;
import java.util.UUID;

public class WorkingHoursAlreadyPresentException extends CustomUnprocessableEntityException {
    public WorkingHoursAlreadyPresentException(UUID placeId, List<DayOfWeek> duplicateDays) {
        super("For place with id " + placeId + " duplicated days are inserted: " + duplicateDays);
    }
}
