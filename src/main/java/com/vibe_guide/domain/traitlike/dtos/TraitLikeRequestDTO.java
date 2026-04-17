package com.vibe_guide.domain.traitlike.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record TraitLikeRequestDTO(
    @NotNull UUID userId, @NotNull UUID placeId, @NotNull List<UUID> traitIds) {}

