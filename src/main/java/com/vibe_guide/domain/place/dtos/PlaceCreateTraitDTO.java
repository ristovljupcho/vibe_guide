package com.vibe_guide.domain.place.dtos;

import com.vibe_guide.enums.TraitPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PlaceCreateTraitDTO(
    @NotNull UUID traitId,
    @NotBlank String additionalInformation,
    @NotNull TraitPriority priority) {}

