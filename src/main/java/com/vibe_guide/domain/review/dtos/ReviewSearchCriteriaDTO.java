package com.vibe_guide.domain.review.dtos;

import java.util.UUID;

public record ReviewSearchCriteriaDTO(UUID placeId, UUID userId, Float rating) {}

