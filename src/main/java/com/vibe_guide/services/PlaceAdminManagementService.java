package com.vibe_guide.services;

import com.vibe_guide.dtos.PlaceAdminRequestDTO;

import java.util.UUID;

public interface PlaceAdminManagementService {
    String insertPlaceAdmin(UUID placeId, PlaceAdminRequestDTO placeAdminRequestDTO);

    String deletePlaceAdmin(UUID placeId, UUID userId);
}