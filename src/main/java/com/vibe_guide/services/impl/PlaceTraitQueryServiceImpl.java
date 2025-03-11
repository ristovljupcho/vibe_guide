package com.vibe_guide.services.impl;

import com.vibe_guide.converters.TraitConverter;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.repositories.PlaceTraitRepository;
import com.vibe_guide.services.PlaceTraitQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceTraitQueryServiceImpl implements PlaceTraitQueryService {

    private final PlaceTraitRepository placeTraitRepository;
    private final TraitConverter traitConverter;

    @Override
    public List<TraitCarouselResponseDTO> getTraitsForPlaceCarousel(UUID placeId) {
        List<Trait> traits = placeTraitRepository.getTraitsForPlaceCarousel(placeId);

        return traits.stream().map(traitConverter::toTraitMenuResponseDTO).toList();
    }
}