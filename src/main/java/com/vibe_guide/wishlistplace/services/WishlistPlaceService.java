package com.vibe_guide.wishlistplace.services;

import com.vibe_guide.wishlistplace.dtos.WishlistPlaceResponseDTO;
import java.util.List;
import java.util.UUID;

public interface WishlistPlaceService {
  List<WishlistPlaceResponseDTO> getAllByUserId(UUID userId);

  String toggle(UUID userId, UUID placeId);
}
