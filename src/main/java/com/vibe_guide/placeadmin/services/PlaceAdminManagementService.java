package com.vibe_guide.placeadmin.services;

import com.vibe_guide.placeadmin.dtos.PlaceAdminRequestDTO;
import java.util.UUID;

public interface PlaceAdminManagementService {
  String insertPlaceAdmin(UUID placeId, PlaceAdminRequestDTO placeAdminRequestDTO);

  String deletePlaceAdmin(UUID placeId, UUID userId);
}
