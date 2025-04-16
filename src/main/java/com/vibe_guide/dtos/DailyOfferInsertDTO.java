package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DailyOfferInsertDTO(
        @NotEmpty
        String name,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotEmpty
        String description,
        byte[] image
) {
}