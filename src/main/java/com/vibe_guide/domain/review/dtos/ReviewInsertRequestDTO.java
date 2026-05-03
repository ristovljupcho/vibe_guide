package com.vibe_guide.domain.review.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ReviewInsertRequestDTO(
    @NotBlank String userId,
    @NotNull UUID placeId,
    @NotBlank String description,
    @NotNull Float rating) {}

