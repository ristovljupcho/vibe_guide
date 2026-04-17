package com.vibe_guide.domain.visitedplace.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record VisitedPlaceResponseDTO(
    UUID placeId,
    String placeName,
    double rating,
    String description,
    LocalDateTime createdAt,
    String note) {}

