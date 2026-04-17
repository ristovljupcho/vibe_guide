package com.vibe_guide.domain.place.services;

import com.vibe_guide.domain.place.dtos.PlaceCreateDTO;
import com.vibe_guide.domain.place.dtos.PlaceUpdateDTO;
import com.vibe_guide.domain.place.dtos.PlaceResponseDTO;
import java.util.UUID;

public interface PlaceManagementService {
  PlaceResponseDTO create(PlaceCreateDTO placeCreateDTO);

  String update(PlaceUpdateDTO placeUpdateDTO);

  String delete(UUID placeId);
}



