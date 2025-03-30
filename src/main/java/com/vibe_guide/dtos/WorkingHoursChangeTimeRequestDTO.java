package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record WorkingHoursChangeTimeRequestDTO(
        @NotNull
        LocalTime startTime,
        @NotNull
        LocalTime endTime
) {
}
