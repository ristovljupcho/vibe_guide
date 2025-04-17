package com.vibe_guide.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserRegisterRequestDTO(
        @NotNull
        String username,
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt
) {
}
