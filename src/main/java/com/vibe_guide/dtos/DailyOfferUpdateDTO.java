package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

public record DailyOfferUpdateDTO(
        @NotEmpty
        UUID dailyOfferId,
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