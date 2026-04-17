package com.vibe_guide.domain.favouriteplace.dtos;

import jakarta.validation.constraints.Size;

public record FavouritePlaceToggleRequestDTO(@Size(max = 255) String note) {}
