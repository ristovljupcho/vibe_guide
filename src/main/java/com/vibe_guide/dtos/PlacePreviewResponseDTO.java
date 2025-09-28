package com.vibe_guide.dtos;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;

import java.util.UUID;

public record PlacePreviewResponseDTO(
        UUID id,
        String name,
        String description,
        double rating,
        PrimaryType primaryType,
        PriceLevel priceLevel,
        String[] topTraits
) {
}