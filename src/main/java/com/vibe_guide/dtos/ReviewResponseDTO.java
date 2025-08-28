package com.vibe_guide.dtos;

import java.time.LocalDate;

public record ReviewResponseDTO(
        String username,
        String placeName,
        Float rating,
        LocalDate dateCreated,
        LocalDate dateModified,
        String description
) {
}
