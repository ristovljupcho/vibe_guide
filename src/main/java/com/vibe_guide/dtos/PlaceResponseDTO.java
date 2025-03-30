package com.vibe_guide.dtos;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;

import java.util.List;
import java.util.UUID;

public record PlaceResponseDTO(
        UUID placeId,
        String name,
        String description,
        String mapsUri,
        String phoneNumber,
        String address,
        double rating,
        String menuLink,
        PrimaryType primaryType,
        PriceLevel priceLevel,
        List<TraitResponseDTO> topTraits,
        List<EventResponseDTO> dailyEvents,
        List<DailyOfferResponseDTO> dailyOffers,
        List<EventResponseDTO> monthlyEvents,
        List<TraitCarouselResponseDTO> carouselTraits
) {
}