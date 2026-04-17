package com.vibe_guide.domain.placeadmin.services;

import com.vibe_guide.domain.placeadmin.dtos.PlaceAdminRequestDTO;
import java.util.UUID;

public interface PlaceAdminManagementService {
  String insert(UUID placeId, PlaceAdminRequestDTO placeAdminRequestDTO);

  String delete(UUID placeId, UUID userId);
}

