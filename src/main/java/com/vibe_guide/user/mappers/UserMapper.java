package com.vibe_guide.user.mappers;

import com.vibe_guide.enums.Role;
import com.vibe_guide.placeadmin.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.user.dtos.UserPreviewResponseDTO;
import com.vibe_guide.user.entities.User;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public UserPreviewResponseDTO toUserPreviewResponseDTO(User user) {
    UUID userId = user.getId();
    String username = user.getUsername();
    String name = user.getName();
    String email = user.getEmail();
    Role role = user.getRole();

    return new UserPreviewResponseDTO(userId, username, name, email, role);
  }

  public PlaceAdminResponseDTO toPlaceAdminResponseDTO(User user) {
    UUID userId = user.getId();
    String email = user.getEmail();
    String username = user.getUsername();

    return new PlaceAdminResponseDTO(userId, email, username);
  }
}
