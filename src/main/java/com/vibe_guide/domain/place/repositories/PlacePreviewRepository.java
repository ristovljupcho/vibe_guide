package com.vibe_guide.domain.place.repositories;

import com.vibe_guide.domain.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import java.util.List;

public interface PlacePreviewRepository {

  List<PlacePreviewResponseDTO> findAllPreviews(
      List<String> traits, PlaceSortBy sortBy, SortDirection sortDirection);

  List<PlacePreviewResponseDTO> findTopPreviews(int limit);
}
