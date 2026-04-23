package com.vibe_guide.domain.favouriteplace.services;

import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceToggleRequestDTO;
import java.util.List;
import java.util.UUID;

public interface FavouritePlaceService {
  List<FavouritePlaceResponseDTO> getAllByUserId(UUID userId);

  String toggle(FavouritePlaceToggleRequestDTO dto);
}

