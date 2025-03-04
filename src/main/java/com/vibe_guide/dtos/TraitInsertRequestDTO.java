package com.vibe_guide.dtos;

import com.vibe_guide.enums.TraitType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TraitInsertRequestDTO(
        @NotNull
        TraitType traitType,
        @NotEmpty
        String name
) {
}