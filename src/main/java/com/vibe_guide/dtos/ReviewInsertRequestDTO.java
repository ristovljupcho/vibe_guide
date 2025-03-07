package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReviewInsertRequestDTO(
        @NotNull
        UUID userId,
        @NotNull
        UUID placeId,
        @NotNull
        String description,
        @NotNull
        Float rating
) {
}
