package com.vibe_guide.domain.placeadmin.services;

import com.vibe_guide.domain.placeadmin.dtos.PlaceAdminResponseDTO;
import java.util.List;
import java.util.UUID;

public interface PlaceAdminQueryService {
  List<PlaceAdminResponseDTO> getAllByPlaceId(UUID placeId);
}

