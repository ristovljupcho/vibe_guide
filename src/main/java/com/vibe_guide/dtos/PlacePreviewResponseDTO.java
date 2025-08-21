package com.vibe_guide.dtos;

import com.vibe_guide.enums.PrimaryType;

import java.util.UUID;

public record PlacePreviewResponseDTO(
        UUID placeId,
        String placeName,
        String description,
        String address,
        double rating,
        PrimaryType primaryType,
        String[] topTraits
) {
}