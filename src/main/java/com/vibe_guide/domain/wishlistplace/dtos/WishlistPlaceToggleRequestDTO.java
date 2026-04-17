package com.vibe_guide.domain.wishlistplace.dtos;

import jakarta.validation.constraints.Size;

public record WishlistPlaceToggleRequestDTO(@Size(max = 255) String note) {}
