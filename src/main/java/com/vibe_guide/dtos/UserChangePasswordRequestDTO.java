package com.vibe_guide.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserChangePasswordRequestDTO(
        @NotNull
        UUID userId,
        @NotNull
        String currentPassword,
        @NotNull
        String newPassword,
        @NotNull
        String confirmNewPassword
) {}
