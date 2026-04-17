package com.vibe_guide.domain.trait.dtos;

import com.vibe_guide.enums.TraitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record TraitUpdateRequestDTO(
    @NotNull UUID traitId, @NotNull TraitType traitType, @NotBlank String name) {}

