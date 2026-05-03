package com.vibe_guide.domain.favouriteplace.services;

import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceToggleRequestDTO;
import java.util.List;

public interface FavouritePlaceService {
  List<FavouritePlaceResponseDTO> getAllByUserId(String userId);

  String toggle(FavouritePlaceToggleRequestDTO dto);
}

