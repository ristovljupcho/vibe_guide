package com.vibe_guide.placetrait.dtos;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record BatchInsertTraitsInPlace(
    @NotEmpty List<PlaceTraitRequestDTO> placeTraitRequestDTOs) {}
