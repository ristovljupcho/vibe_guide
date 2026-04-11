package com.vibe_guide.place.services;

import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.place.dtos.PlaceResponseDTO;
import java.util.List;
import java.util.UUID;

public interface PlaceQueryService {
  PlaceResponseDTO getPlaceById(UUID placeId);

  List<PlacePreviewResponseDTO> getPlaces(
      List<String> traits, PlaceSortBy sortBy, SortDirection sortDirection);

  List<PlacePreviewResponseDTO> getTopPlaces();
}
