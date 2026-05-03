package com.vibe_guide.domain.place.services;

import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.domain.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.domain.place.dtos.PlaceResponseDTO;
import java.util.List;
import java.util.UUID;

public interface PlaceQueryService {
  PlaceResponseDTO getById(UUID placeId);

  List<PlacePreviewResponseDTO> getAll(
      List<String> traits, PlaceSortBy sortBy, SortDirection sortDirection);

  List<PlacePreviewResponseDTO> getTop();
}


