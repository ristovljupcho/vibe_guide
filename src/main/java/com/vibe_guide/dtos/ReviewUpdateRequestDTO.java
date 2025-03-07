package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReviewUpdateRequestDTO(
        @NotNull
        UUID reviewId,
        @NotEmpty
        String description,
        @NotNull
        Float rating
) {
}
