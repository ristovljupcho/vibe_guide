package com.vibe_guide.dtos;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record PlaceUpdateInfoRequestDTO(
        @NotEmpty
        UUID placeId,
        @NotEmpty
        String name,
        @NotEmpty
        String description,
        @NotEmpty
        String mapsUri,
        @NotEmpty
        String phoneNumber,
        @NotEmpty
        String address,
        @NotEmpty
        String menuLink,
        @NotEmpty
        PrimaryType primaryType,
        @NotEmpty
        PriceLevel priceLevel
) {
}