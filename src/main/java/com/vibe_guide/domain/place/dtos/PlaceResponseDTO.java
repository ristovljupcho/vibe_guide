package com.vibe_guide.domain.place.dtos;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import java.util.List;

public record PlaceResponseDTO(
    String name,
    String description,
    String mapsUri,
    String phoneNumber,
    String address,
    double rating,
    String menuLink,
    PrimaryType primaryType,
    PriceLevel priceLevel,
    List<String> imageUrls) {}

