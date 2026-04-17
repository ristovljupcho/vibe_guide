package com.vibe_guide.domain.placetrait.services.impl;

import com.vibe_guide.domain.placetrait.services.PlaceTraitQueryService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.placetrait.repositories.PlaceTraitRepository;
import com.vibe_guide.domain.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.domain.trait.dtos.TraitResponseDTO;
import com.vibe_guide.domain.trait.entities.Trait;
import com.vibe_guide.domain.trait.mappers.TraitMapper;
import com.vibe_guide.domain.traitlike.entities.TraitLikesSummary;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceTraitQueryServiceImpl implements PlaceTraitQueryService {

  private final PlaceTraitRepository placeTraitRepository;
  private final TraitMapper traitMapper;
  private final PlaceRepository placeRepository;

  @Override
  public List<TraitCarouselResponseDTO> getAllForCarouselByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);
    List<Trait> traits = placeTraitRepository.getTraitsForPlaceCarousel(placeId);

    return traits.stream().map(traitMapper::toTraitCarouselResponseDTO).toList();
  }

  @Override
  public List<TraitResponseDTO> getTopByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);
    List<Trait> traits = placeTraitRepository.getTopByPlaceId(placeId);

    return traits.stream().map(traitMapper::toTraitResponseDTO).toList();
  }

  @Override
  public List<TraitResponseDTO> getMissingByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);
    List<Trait> missingTraits = placeTraitRepository.getMissingByPlaceId(placeId);

    return missingTraits.stream().map(traitMapper::toTraitResponseDTO).toList();
  }

  @Override
  public List<TraitResponseDTO> getMostPopular() {
    List<TraitLikesSummary> traitLikesSummaries =
        placeTraitRepository.getTopTraits().stream().limit(10).toList();

    return traitLikesSummaries.stream().map(traitMapper::toTraitResponseDTO).toList();
  }

  void checkIfPlaceExists(UUID placeId) {
    if (!placeRepository.existsById(placeId)) {
      throw new PlaceNotFoundException(placeId);
    }
  }
}


