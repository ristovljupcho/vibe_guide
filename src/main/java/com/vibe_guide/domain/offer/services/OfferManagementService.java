package com.vibe_guide.domain.offer.services;

import com.vibe_guide.domain.offer.dtos.OfferInsertDTO;
import com.vibe_guide.domain.offer.dtos.OfferUpdateDTO;
import java.util.UUID;

public interface OfferManagementService {
  String insert(OfferInsertDTO dto);

  String update(OfferUpdateDTO dto);

  String delete(UUID offerId);
}

