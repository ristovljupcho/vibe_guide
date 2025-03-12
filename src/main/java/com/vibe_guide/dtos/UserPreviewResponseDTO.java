package com.vibe_guide.dtos;

import com.vibe_guide.enums.Role;

import java.util.UUID;

public record UserPreviewResponseDTO(
        UUID userId,
        String username,
        String name,
        String email,
        Role role
) {
}
