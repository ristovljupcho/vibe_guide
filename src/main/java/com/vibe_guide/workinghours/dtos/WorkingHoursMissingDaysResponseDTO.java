package com.vibe_guide.workinghours.dtos;

import com.vibe_guide.enums.DayOfWeek;

public record WorkingHoursMissingDaysResponseDTO(
        DayOfWeek dayOfWeek
) {
}