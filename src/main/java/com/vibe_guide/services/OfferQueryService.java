package com.vibe_guide.services;

import com.vibe_guide.dtos.OfferResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OfferQueryService {
    List<OfferResponseDTO> getActiveOffersByPlaceId(UUID placeId);

    List<OfferResponseDTO> getAllActiveOffers();

    List<OfferResponseDTO> getUpcomingOffersByPlaceId(UUID placeId);

    List<OfferResponseDTO> getAllUpcomingOffers();
}