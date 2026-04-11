package com.vibe_guide.offer.services;

import com.vibe_guide.offer.dtos.OfferResponseDTO;
import java.util.List;
import java.util.UUID;

public interface OfferQueryService {
  List<OfferResponseDTO> getActiveByPlaceId(UUID placeId);

  List<OfferResponseDTO> getActive();

  List<OfferResponseDTO> getUpcomingByPlaceId(UUID placeId);

  List<OfferResponseDTO> getUpcoming();
}
