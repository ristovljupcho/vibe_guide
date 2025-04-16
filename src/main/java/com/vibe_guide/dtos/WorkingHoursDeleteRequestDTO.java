package com.vibe_guide.dtos;

import com.vibe_guide.enums.DayOfWeek;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record WorkingHoursDeleteRequestDTO(
        @NotEmpty
        List<DayOfWeek> daysToDelete
) {
}