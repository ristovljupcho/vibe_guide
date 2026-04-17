package com.vibe_guide.domain.workinghours.dtos;

import com.vibe_guide.enums.DayOfWeek;
import java.time.LocalTime;

public record WorkingHoursResponseDTO(
    DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {}

