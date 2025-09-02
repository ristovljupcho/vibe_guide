package com.vibe_guide.dtos;

import java.time.LocalDateTime;

public record EventResponseDTO(
        String name,
        String placeName,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        byte[] image
) {
}