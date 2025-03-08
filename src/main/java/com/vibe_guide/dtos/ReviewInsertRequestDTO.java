package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReviewInsertRequestDTO(
        @NotNull
        UUID userId,
        @NotNull
        UUID placeId,
        @NotBlank
        String description,
        @NotNull
        Float rating
) {
}
