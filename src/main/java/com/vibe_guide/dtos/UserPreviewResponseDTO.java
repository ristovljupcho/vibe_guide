package com.vibe_guide.dtos;

import com.vibe_guide.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserPreviewResponseDTO(
        UUID id,
        String username,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Role role
) {

}
