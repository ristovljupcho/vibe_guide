package com.vibe_guide.dtos;

import com.vibe_guide.enums.Role;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserRegisterRequestDTO(
        @NotNull
        String username,
        @NotNull
        String name,
        @NotNull
        String email,
//        @NotNull
//        Role role,
        @NotNull
        String password,
        @NotNull
        LocalDateTime created_at
) {
}
