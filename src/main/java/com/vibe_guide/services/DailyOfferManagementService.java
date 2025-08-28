package com.vibe_guide.services;

import com.vibe_guide.dtos.DailyOfferInsertDTO;
import com.vibe_guide.dtos.DailyOfferUpdateDTO;

import java.util.UUID;

public interface DailyOfferManagementService {
    String insertOffer(DailyOfferInsertDTO dto);

    String updateDailyOffer(DailyOfferUpdateDTO dto);

    String deleteDailyOffer(UUID dailyOfferId);
}