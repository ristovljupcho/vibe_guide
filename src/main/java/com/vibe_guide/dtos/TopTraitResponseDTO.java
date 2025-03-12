package com.vibe_guide.dtos;

import java.util.UUID;

public record TopTraitResponseDTO(
        UUID traitId,
        String name,
        int totalLikes
) {
}