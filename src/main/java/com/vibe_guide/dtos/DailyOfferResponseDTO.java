package com.vibe_guide.dtos;

import java.time.LocalDateTime;

public record DailyOfferResponseDTO(
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String description,
        byte[] image
){
}