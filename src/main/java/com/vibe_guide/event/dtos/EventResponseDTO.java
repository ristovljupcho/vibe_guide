package com.vibe_guide.event.dtos;

import java.time.LocalDateTime;

public record EventResponseDTO(
    String name,
    String placeName,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    byte[] image) {}
