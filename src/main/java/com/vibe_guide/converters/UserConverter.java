package com.vibe_guide.converters;

import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.Role;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserConverter {
    public UserPreviewResponseDTO toUserPreviewResponseDTO(User user) {
        UUID userId = user.getId();
        String username = user.getUsername();
        String name = user.getName();
        String email = user.getEmail();
        Role role = user.getRole();

        return new UserPreviewResponseDTO(userId, username, name, email, role);
    }
}
