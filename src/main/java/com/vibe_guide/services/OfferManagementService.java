package com.vibe_guide.services;

import com.vibe_guide.dtos.OfferInsertDTO;
import com.vibe_guide.dtos.OfferUpdateDTO;

import java.util.UUID;

public interface OfferManagementService {
    String insertOffer(OfferInsertDTO dto);

    String updateDailyOffer(OfferUpdateDTO dto);

    String deleteDailyOffer(UUID offerId);
}