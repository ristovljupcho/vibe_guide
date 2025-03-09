package com.vibe_guide.services;

import com.vibe_guide.dtos.DailyOfferResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DailyOfferQueryService {
    Page<DailyOfferResponseDTO> getDailyOffers(UUID placeId, int page, int size);
}