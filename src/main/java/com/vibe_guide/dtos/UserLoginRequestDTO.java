package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDTO(
        @NotBlank
        String username,
        @NotNull
        String password
) {
}
