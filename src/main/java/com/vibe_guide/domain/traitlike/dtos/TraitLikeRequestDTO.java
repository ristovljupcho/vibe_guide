package com.vibe_guide.domain.traitlike.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record TraitLikeRequestDTO(
    @NotBlank String userId, @NotNull UUID placeId, @NotNull List<UUID> traitIds) {}

