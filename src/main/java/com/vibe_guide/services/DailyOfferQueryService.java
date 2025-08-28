package com.vibe_guide.services;

import com.vibe_guide.dtos.DailyOfferResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DailyOfferQueryService {
    List<DailyOfferResponseDTO> getDailyOffersByPlaceId(UUID placeId);

    List<DailyOfferResponseDTO> getDailyOffers();

    List<DailyOfferResponseDTO> getUpcomingOffersByPlaceId(UUID placeId);

    List<DailyOfferResponseDTO> getUpcomingOffers();
}