package com.vibe_guide.dtos;

import java.util.UUID;

public record PlaceAdminResponseDTO(
        UUID userId,
        String email
) {
}