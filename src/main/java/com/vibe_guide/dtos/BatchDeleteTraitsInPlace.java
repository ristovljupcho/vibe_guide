package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record BatchDeleteTraitsInPlace(
        @NotEmpty
        List<UUID> traitIds
) {
}