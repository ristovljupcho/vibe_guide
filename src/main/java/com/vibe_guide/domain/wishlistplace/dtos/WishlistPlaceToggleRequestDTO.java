package com.vibe_guide.domain.wishlistplace.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record WishlistPlaceToggleRequestDTO(
    @NotNull UUID userId, @NotNull UUID placeId, @Size(max = 255) String note) {}
