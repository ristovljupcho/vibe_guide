package com.vibe_guide.services;

import com.vibe_guide.dtos.DailyOfferResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DailyOfferQueryService {
    List<DailyOfferResponseDTO> getTodayOffersByPlaceId(UUID placeId);

    List<DailyOfferResponseDTO> findUpcomingOffersByPlaceId(UUID placeId);

    List<DailyOfferResponseDTO> getTodayDailyOffers();
}