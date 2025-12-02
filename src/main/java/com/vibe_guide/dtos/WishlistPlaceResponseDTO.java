package com.vibe_guide.dtos;

import java.util.UUID;

public record WishlistPlaceResponseDTO(
        UUID placeId,
        String name,
        double rating,
        String description
) {}
