package com.vibe_guide.placeadmin.services;

import com.vibe_guide.placeadmin.dtos.PlaceAdminResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PlaceAdminQueryService {
    List<PlaceAdminResponseDTO> getAllAdminsForPlace(UUID placeId);
}