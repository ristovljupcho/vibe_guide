package com.vibe_guide.services;

import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.dtos.PlaceUpdateInfoRequestDTO;

import java.util.UUID;

public interface PlaceManagementService {
    //todo: implement this method using API calls
    PlaceResponseDTO insertPlace();

    PlaceResponseDTO updatePlace(PlaceUpdateInfoRequestDTO placeUpdateInfoRequestDTO);

    String deletePlace(UUID placeId);
}