package com.vibe_guide.domain.event.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponseDTO(
    UUID id,
    String name,
    String placeName,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    String imageUrl) {}
