package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserChangePasswordRequestDTO(
        @NotNull
        UUID userId,
        @NotBlank
        String currentPassword,
        @NotBlank
        String newPassword,
        @NotBlank
        String confirmNewPassword
) {}
