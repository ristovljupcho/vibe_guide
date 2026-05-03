package com.vibe_guide.domain.user.dtos;

import jakarta.validation.constraints.NotBlank;

/** Payload sent by the frontend after a successful Clerk sign-in to sync the user into our DB. */
public record UserUpsertRequestDTO(
    @NotBlank String id,
    @NotBlank String username,
    @NotBlank String name,
    @NotBlank String email) {}
