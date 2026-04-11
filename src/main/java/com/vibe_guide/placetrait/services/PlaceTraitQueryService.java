package com.vibe_guide.placetrait.services;

import com.vibe_guide.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.trait.dtos.TraitResponseDTO;
import java.util.List;
import java.util.UUID;

public interface PlaceTraitQueryService {
  List<TraitCarouselResponseDTO> getAllForCarouselByPlaceId(UUID placeId);

  List<TraitResponseDTO> getTopByPlaceId(UUID placeId);

  List<TraitResponseDTO> getMissingByPlaceId(UUID placeId);

  List<TraitResponseDTO> getMostPopular();
}
