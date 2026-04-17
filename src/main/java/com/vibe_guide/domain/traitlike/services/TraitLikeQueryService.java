package com.vibe_guide.domain.traitlike.services;

import com.vibe_guide.domain.trait.dtos.TraitResponseDTO;
import java.util.List;
import java.util.UUID;

public interface TraitLikeQueryService {
  List<TraitResponseDTO> getAllByPlaceIdAndUserId(UUID placeId, UUID userId);
}

