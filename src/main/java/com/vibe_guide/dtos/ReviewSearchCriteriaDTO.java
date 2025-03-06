package com.vibe_guide.dtos;

import java.util.UUID;

public record ReviewSearchCriteriaDTO(
        UUID placeId,
        UUID userId,
        Float rating
) {
}
