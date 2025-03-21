package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventUpdateRequestDTO(
        @NotNull
        UUID eventId,
        @NotEmpty
        String name,
        @NotEmpty
        String description,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        UUID placeId
) {
}
