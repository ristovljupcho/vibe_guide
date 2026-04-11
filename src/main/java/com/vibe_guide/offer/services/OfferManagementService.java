package com.vibe_guide.offer.services;

import com.vibe_guide.offer.dtos.OfferInsertDTO;
import com.vibe_guide.offer.dtos.OfferUpdateDTO;
import java.util.UUID;

public interface OfferManagementService {
  String insert(OfferInsertDTO dto);

  String update(OfferUpdateDTO dto);

  String delete(UUID offerId);
}
