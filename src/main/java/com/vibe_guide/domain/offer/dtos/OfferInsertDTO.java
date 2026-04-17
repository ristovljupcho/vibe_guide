package com.vibe_guide.domain.offer.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record OfferInsertDTO(
    @NotNull UUID placeId,
    @NotEmpty String name,
    @NotNull LocalDateTime startDate,
    @NotNull LocalDateTime endDate,
    @NotEmpty String description,
    String imageUrl) {}

