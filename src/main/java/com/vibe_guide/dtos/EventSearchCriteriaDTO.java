package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventSearchCriteriaDTO(
        UUID placeId,
        @NotEmpty
        String eventName,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}