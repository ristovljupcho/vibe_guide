package com.vibe_guide.domain.wishlistplace.services;

import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceResponseDTO;
import java.util.List;
import java.util.UUID;

public interface WishlistPlaceService {
  List<WishlistPlaceResponseDTO> getAllByUserId(UUID userId);

  String toggle(UUID userId, UUID placeId);
}

