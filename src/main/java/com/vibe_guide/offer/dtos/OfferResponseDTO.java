package com.vibe_guide.offer.dtos;

import java.time.LocalDateTime;

public record OfferResponseDTO(
        String name,
        String placeName,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        byte[] image
) {
}