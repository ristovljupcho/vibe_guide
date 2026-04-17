package com.vibe_guide.domain.wishlistplace.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record WishlistPlaceResponseDTO(
    UUID placeId, String name, double rating, String description, LocalDateTime createdAt) {}

