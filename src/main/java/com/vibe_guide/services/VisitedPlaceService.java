package com.vibe_guide.services;

import com.vibe_guide.dtos.VisitedPlaceResponseDTO;

import java.util.List;
import java.util.UUID;

public interface VisitedPlaceService {

    List<VisitedPlaceResponseDTO> getVisitedPlacesByUserId(UUID userId);

    String manageVisitedPlace(UUID userId, UUID placeId);
}