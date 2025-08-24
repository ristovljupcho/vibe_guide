package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record DailyOfferUpdateDTO(
        @NotNull
        UUID placeId,
        @NotNull
        UUID dailyOfferId,
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