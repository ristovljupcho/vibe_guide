package com.vibe_guide.dtos;

import com.vibe_guide.entities.Place;

import java.time.LocalDate;
import java.util.UUID;

public record ReviewPreviewResponseDTO(
        String username,
        UUID placeId,
        Float rating,
        LocalDate dateCreated,
        LocalDate dateModified,
        String description
) {
}
