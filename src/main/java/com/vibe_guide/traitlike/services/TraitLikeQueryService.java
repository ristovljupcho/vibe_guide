package com.vibe_guide.traitlike.services;

import com.vibe_guide.trait.dtos.TraitResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TraitLikeQueryService {
    List<TraitResponseDTO> findAllByPlaceAndUser(UUID placeId, UUID userId);
}
