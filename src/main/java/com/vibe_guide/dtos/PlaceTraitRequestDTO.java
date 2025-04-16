package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PlaceTraitRequestDTO(
        @NotNull
        UUID traitId,
        @NotBlank
        String additionalInformation,
        @NotNull
        boolean priority
) {
}