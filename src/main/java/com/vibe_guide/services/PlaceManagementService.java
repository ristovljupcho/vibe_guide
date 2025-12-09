package com.vibe_guide.services;

import com.vibe_guide.dtos.PlaceRequestDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;

import java.util.UUID;

public interface PlaceManagementService {

    /**
     * Creates a new {@link com.vibe_guide.entities.Place} using provided data.
     *
     * @param placeRequestDTO DTO containing details for the place insertion.
     * @return DTO representing the newly created place.
     */
    PlaceResponseDTO insertPlace(PlaceRequestDTO placeRequestDTO);

    String updatePlace(PlaceRequestDTO placeRequestDTO);

    String deletePlace(UUID placeId);
}