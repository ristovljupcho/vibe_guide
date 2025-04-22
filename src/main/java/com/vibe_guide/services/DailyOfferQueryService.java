package com.vibe_guide.services;

import com.vibe_guide.dtos.DailyOfferResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DailyOfferQueryService {
    List<DailyOfferResponseDTO> getTodayDailyOffersByPlaceId(UUID placeId);
    List<DailyOfferResponseDTO> getTodayDailyOffers();
}