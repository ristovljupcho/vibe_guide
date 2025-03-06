package com.vibe_guide.dtos;

import java.time.LocalDateTime;

public record EventResponseDTO(
        String eventName,
        String placeName,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
){
}