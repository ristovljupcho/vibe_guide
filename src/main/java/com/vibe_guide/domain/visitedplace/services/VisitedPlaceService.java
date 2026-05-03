package com.vibe_guide.domain.visitedplace.services;

import com.vibe_guide.domain.visitedplace.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.domain.visitedplace.dtos.VisitedPlaceToggleRequestDTO;
import java.util.List;

public interface VisitedPlaceService {
  List<VisitedPlaceResponseDTO> getAllByUserId(String userId);

  String toggle(VisitedPlaceToggleRequestDTO dto);
}

