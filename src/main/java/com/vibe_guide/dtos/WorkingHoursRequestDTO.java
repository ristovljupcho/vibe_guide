package com.vibe_guide.dtos;

import com.vibe_guide.enums.DayOfWeek;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record WorkingHoursRequestDTO(
        @NotNull
        DayOfWeek dayOfWeek,
        @NotNull
        LocalTime startTime,
        @NotNull
        LocalTime endTime
) {
}
