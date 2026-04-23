package com.vibe_guide.domain.visitedplace.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record VisitedPlaceToggleRequestDTO(
    @NotNull UUID userId, @NotNull UUID placeId, @Size(max = 255) String note) {}
