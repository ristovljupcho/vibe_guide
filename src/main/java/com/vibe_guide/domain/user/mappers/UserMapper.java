package com.vibe_guide.domain.user.mappers;

import com.vibe_guide.enums.Role;
import com.vibe_guide.domain.placeadmin.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.domain.user.dtos.UserPreviewResponseDTO;
import com.vibe_guide.domain.user.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public UserPreviewResponseDTO toUserPreviewResponseDTO(User user) {
    String userId = user.getId();
    String username = user.getUsername();
    String name = user.getName();
    String email = user.getEmail();
    Role role = user.getRole();

    return new UserPreviewResponseDTO(userId, username, name, email, role);
  }

  public PlaceAdminResponseDTO toPlaceAdminResponseDTO(User user) {
    String userId = user.getId();
    String email = user.getEmail();
    String username = user.getUsername();

    return new PlaceAdminResponseDTO(userId, email, username);
  }
}
