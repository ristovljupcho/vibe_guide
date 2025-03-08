package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReviewUpdateRequestDTO(
        @NotNull
        UUID reviewId,
        @NotBlank
        String description,
        @NotNull
        Float rating
) {
}
