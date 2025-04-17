package com.vibe_guide.services;

import com.vibe_guide.dtos.PlaceRequestDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;

import java.util.UUID;

public interface PlaceManagementService {
    //todo: implement this method using API calls
    PlaceResponseDTO insertPlace();

    PlaceResponseDTO updatePlace(PlaceRequestDTO placeRequestDTO);

    String deletePlace(UUID placeId);
}