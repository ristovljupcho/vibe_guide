package com.vibe_guide.domain.user.dtos;

import com.vibe_guide.enums.Role;

public record UserPreviewResponseDTO(
    String userId, String username, String name, String email, Role role) {}
