package com.vibe_guide.dtos;

import com.vibe_guide.enums.PrimaryType;

import java.util.List;
import java.util.UUID;

public record PlacePreviewResponseDTO(
        UUID placeId,
        String name,
        double rating,
        PrimaryType primaryType,
        List<String> topTraits
) {
}