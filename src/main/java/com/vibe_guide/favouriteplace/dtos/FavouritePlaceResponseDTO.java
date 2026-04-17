package com.vibe_guide.favouriteplace.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record FavouritePlaceResponseDTO(
    UUID placeId, String name, double rating, String description, LocalDateTime createdAt) {}
