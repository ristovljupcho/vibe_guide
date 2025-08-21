package com.vibe_guide.services;

import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PlaceQueryService {
    PlaceResponseDTO getPlaceById(UUID placeId);

    List<PlacePreviewResponseDTO> getTopPlaces();
}