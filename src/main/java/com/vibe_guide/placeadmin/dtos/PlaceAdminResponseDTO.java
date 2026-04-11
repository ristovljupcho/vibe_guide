package com.vibe_guide.placeadmin.dtos;

import java.util.UUID;

public record PlaceAdminResponseDTO(
        UUID userId,
        String email,
        String username
) {
}