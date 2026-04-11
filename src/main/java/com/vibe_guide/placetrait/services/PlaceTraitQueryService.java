package com.vibe_guide.placetrait.services;

import com.vibe_guide.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.trait.dtos.TraitResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PlaceTraitQueryService {
    List<TraitCarouselResponseDTO> getTraitsForDisplayInPlaceCarousel(UUID placeId);

    List<TraitResponseDTO> getTopTraitsForPlace(UUID placeId);

    List<TraitResponseDTO> getMissingTraitsForPlace(UUID placeId);

    List<TraitResponseDTO> getMostPopularTraits();
}