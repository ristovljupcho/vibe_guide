package com.vibe_guide.domain.wishlistplace.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record WishlistPlaceToggleRequestDTO(
    @NotBlank String userId, @NotNull UUID placeId, @Size(max = 255) String note) {}
