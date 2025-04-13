package com.vibe_guide.services;

import com.vibe_guide.dtos.PlaceAdminResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PlaceAdminQueryService {
    List<PlaceAdminResponseDTO> getAllAdminsForPlace(UUID placeId);
}