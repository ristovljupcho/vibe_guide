package com.vibe_guide.favouriteplace.services;

import com.vibe_guide.favouriteplace.dtos.FavouritePlaceResponseDTO;
import java.util.List;
import java.util.UUID;

public interface FavouritePlaceService {
  List<FavouritePlaceResponseDTO> getFavouritePlacesByUserId(UUID userId);

  String manageFavouritePlace(UUID userId, UUID placeId);
}
