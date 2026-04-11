package com.vibe_guide.review.dtos;

import java.util.UUID;

public record ReviewSearchCriteriaDTO(UUID placeId, UUID userId, Float rating) {}
