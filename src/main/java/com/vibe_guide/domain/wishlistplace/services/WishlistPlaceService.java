package com.vibe_guide.domain.wishlistplace.services;

import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceToggleRequestDTO;
import java.util.List;

public interface WishlistPlaceService {
  List<WishlistPlaceResponseDTO> getAllByUserId(String userId);

  String toggle(WishlistPlaceToggleRequestDTO dto);
}

