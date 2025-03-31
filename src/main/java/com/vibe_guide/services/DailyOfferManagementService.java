package com.vibe_guide.services;

import com.vibe_guide.dtos.DailyOfferInsertDTO;
import com.vibe_guide.dtos.DailyOfferUpdateDTO;

import java.util.UUID;

public interface DailyOfferManagementService {
    String insertDailyOffer(UUID placeId, DailyOfferInsertDTO dailyOfferInsertDTO);

    String updateDailyOffer(UUID placeId, UUID dailyOfferId, DailyOfferUpdateDTO dailyOfferUpdateDTO);

    String deleteDailyOffer(UUID dailyOfferId);
}