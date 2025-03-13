package com.vibe_guide.services;

import com.vibe_guide.dtos.TopTraitResponseDTO;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PlaceTraitQueryService {
    List<TraitCarouselResponseDTO> getTraitsForPlaceCarousel(UUID placeId);

    List<TopTraitResponseDTO> getMostPopularTraits();
}