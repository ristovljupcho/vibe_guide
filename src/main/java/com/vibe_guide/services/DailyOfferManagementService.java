package com.vibe_guide.services;

import com.vibe_guide.dtos.DailyOfferInsertDTO;
import com.vibe_guide.dtos.DailyOfferUpdateDTO;

import java.util.UUID;

public interface DailyOfferManagementService {
    String insertDailyOffer(DailyOfferInsertDTO dailyOfferInsertDTO);

    String updateDailyOffer(UUID dailyOfferId, DailyOfferUpdateDTO dailyOfferUpdateDTO);

    String deleteDailyOffer(UUID dailyOfferId);
}