package com.vibe_guide.services;

import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.entities.Place;

public interface PlaceConverterWithAttributes {
    PlaceResponseDTO getPlaceResponseDTO(Place place);
}