package com.vibe_guide.domain.place.services.impl;

import com.vibe_guide.domain.place.services.PlaceQueryService;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.domain.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.domain.place.dtos.PlaceResponseDTO;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.mappers.PlaceMapper;
import com.vibe_guide.domain.place.repositories.PlacePreviewRepository;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceQueryServiceImpl implements PlaceQueryService {

  private static final int TOP_PLACES_LIMIT = 10;

  private final PlaceRepository placeRepository;
  private final PlacePreviewRepository placePreviewRepository;
  private final PlaceMapper placeMapper;

  @Override
  public PlaceResponseDTO getById(UUID placeId) {
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    return placeMapper.toPlaceResponseDTO(place);
  }

  @Override
  public List<PlacePreviewResponseDTO> getAll(
      List<String> traits, PlaceSortBy sortBy, SortDirection sortDirection) {
    return placePreviewRepository.findAllPreviews(traits, sortBy, sortDirection);
  }

  @Override
  public List<PlacePreviewResponseDTO> getTop() {
    return placePreviewRepository.findTopPreviews(TOP_PLACES_LIMIT);
  }
}
