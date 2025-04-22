package com.vibe_guide.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponseDTO(
        String eventName,
        String placeName,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<String> images
){
}