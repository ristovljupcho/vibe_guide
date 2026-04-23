package com.vibe_guide.domain.wishlistplace.services;

import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceToggleRequestDTO;
import java.util.List;
import java.util.UUID;

public interface WishlistPlaceService {
  List<WishlistPlaceResponseDTO> getAllByUserId(UUID userId);

  String toggle(WishlistPlaceToggleRequestDTO dto);
}

