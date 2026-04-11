package com.vibe_guide.place.services;

import com.vibe_guide.place.dtos.PlaceRequestDTO;
import com.vibe_guide.place.dtos.PlaceResponseDTO;
import java.util.UUID;

public interface PlaceManagementService {
  // todo: implement this method using API calls
  PlaceResponseDTO insertPlace();

  String updatePlace(PlaceRequestDTO placeRequestDTO);

  String deletePlace(UUID placeId);
}
