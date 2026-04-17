package com.vibe_guide.domain.visitedplace.dtos;

import jakarta.validation.constraints.Size;

public record VisitedPlaceToggleRequestDTO(@Size(max = 255) String note) {}
