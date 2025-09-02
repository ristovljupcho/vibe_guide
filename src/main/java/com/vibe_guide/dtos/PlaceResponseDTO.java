package com.vibe_guide.dtos;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;

public record PlaceResponseDTO(
        String name,
        String description,
        String mapsUri,
        String phoneNumber,
        String address,
        double rating,
        String menuLink,
        PrimaryType primaryType,
        PriceLevel priceLevel
) {
}