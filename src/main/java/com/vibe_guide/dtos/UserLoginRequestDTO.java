package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDTO(
        @NotNull
        String email,
        @NotNull
        String username,
        @NotNull
        String password
) {
}
