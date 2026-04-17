package com.vibe_guide.domain.trait.dtos;

import com.vibe_guide.enums.TraitType;
import java.util.UUID;

public record TraitResponseDTO(UUID traitId, TraitType traitType, String name) {}

