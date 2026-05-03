package com.vibe_guide.domain.offer.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record OfferResponseDTO(
    UUID id,
    String name,
    String placeName,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    String imageUrl) {}
