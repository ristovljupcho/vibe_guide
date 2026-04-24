package com.vibe_guide.domain.place.dtos;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;

import java.util.List;
import java.util.UUID;

public record PlacePreviewResponseDTO(
        UUID id,
        String name,
        String description,
        String address,
        double rating,
        PrimaryType primaryType,
        PriceLevel priceLevel,
        List<String> imageUrls,
        List<String> topTraits) {}
