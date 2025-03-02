package com.vibe_guide.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record ReviewPreviewResponseDTO(
        UUID id,
        LocalDate dateCreated,
        LocalDate dateModified,
        Float rating,
        String description,
        UUID placeId,
        String username
) {
}
