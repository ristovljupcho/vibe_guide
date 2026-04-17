package com.vibe_guide.domain.visitedplace.services;

import com.vibe_guide.domain.visitedplace.dtos.VisitedPlaceResponseDTO;
import java.util.List;
import java.util.UUID;

public interface VisitedPlaceService {
  List<VisitedPlaceResponseDTO> getAllByUserId(UUID userId);

  String toggle(UUID userId, UUID placeId, String note);
}

