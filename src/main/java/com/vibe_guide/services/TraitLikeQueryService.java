package com.vibe_guide.services;

import com.vibe_guide.dtos.TraitResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TraitLikeQueryService {
    List<TraitResponseDTO> findAllByPlaceAndUser(UUID placeId, UUID userId);
}
