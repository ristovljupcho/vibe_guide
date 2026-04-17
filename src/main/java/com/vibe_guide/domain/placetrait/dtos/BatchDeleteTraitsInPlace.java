package com.vibe_guide.domain.placetrait.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record BatchDeleteTraitsInPlace(@NotNull UUID placeId, @NotEmpty List<UUID> traitIds) {}


