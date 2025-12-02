package com.vibe_guide.dtos;

import java.util.UUID;

public record FavouritePlaceResponseDTO(
        UUID placeId,
        String name,
        double rating,
        String description
) {}
