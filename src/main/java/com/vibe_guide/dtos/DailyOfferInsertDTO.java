package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record DailyOfferInsertDTO(
        @NotNull
        UUID placeId,
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