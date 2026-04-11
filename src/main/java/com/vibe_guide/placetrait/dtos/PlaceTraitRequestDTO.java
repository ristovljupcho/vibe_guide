package com.vibe_guide.placetrait.dtos;

import com.vibe_guide.enums.TraitPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PlaceTraitRequestDTO(
    @NotNull UUID placeId,
    @NotNull UUID traitId,
    @NotBlank String additionalInformation,
    @NotNull TraitPriority priority) {}
