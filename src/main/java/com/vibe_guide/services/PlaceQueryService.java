package com.vibe_guide.services;

import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PlaceQueryService {
    PlaceResponseDTO getPlaceById(UUID placeId);

    Page<PlacePreviewResponseDTO> getPaginatedPlaces(List<String> traits, PlaceSortBy sortBy,
                                                     SortDirection sortDirection, int page, int size);
}