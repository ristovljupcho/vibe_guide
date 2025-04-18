package com.vibe_guide.converters;

import com.vibe_guide.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.Role;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <ul>
 * <li>
 * This class is responsible for providing conversion methods from {@link User} entity
 * to Data Transfer Objects and vice versa.
 * </li>
 * </ul>
 */
@Component
public class UserConverter {
    /**
     * * Converts a {@link User} entity to a {@link UserPreviewResponseDTO} entity.
     *
     * @param user {@link User} entity to convert.
     * @return The converted {@link UserPreviewResponseDTO} entity.
     */
    public UserPreviewResponseDTO toUserPreviewResponseDTO(User user) {
        UUID userId = user.getId();
        String username = user.getUsername();
        String name = user.getName();
        String email = user.getEmail();
        Role role = user.getRole();

        return new UserPreviewResponseDTO(userId, username, name, email, role);
    }

    /**
     * * Converts a {@link User} entity to a {@link PlaceAdminResponseDTO} entity.
     *
     * @param user {@link User} entity to convert.
     * @return The converted {@link PlaceAdminResponseDTO} entity.
     */
    public PlaceAdminResponseDTO toPlaceAdminResponseDTO(User user) {
        UUID userId = user.getId();
        String email = user.getEmail();
        String username = user.getUsername();

        return new PlaceAdminResponseDTO(userId, email, username);
    }
}