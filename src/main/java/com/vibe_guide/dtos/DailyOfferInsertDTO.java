package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record DailyOfferInsertDTO(
        @NotEmpty
        String name,
        @NotEmpty
        LocalDateTime startDate,
        @NotEmpty
        LocalDateTime endDate,
        @NotEmpty
        String description,
        byte[] image
) {
}