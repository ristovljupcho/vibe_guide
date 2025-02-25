package com.vibe_guide.dtos;

import com.vibe_guide.enums.TraitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TraitInsertRequestDTO(
        @NotNull
        TraitType traitType,
        @NotBlank
        String name
) {
}
