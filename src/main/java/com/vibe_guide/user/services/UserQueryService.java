package com.vibe_guide.user.services;

import com.vibe_guide.enums.Role;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.UserSortBy;
import com.vibe_guide.user.dtos.UserPreviewResponseDTO;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface UserQueryService {

  Page<UserPreviewResponseDTO> getPaginated(
      Role role, UserSortBy sortBy, SortDirection sortDirection, int page, int size);

  UserPreviewResponseDTO getById(UUID userId);

  UserPreviewResponseDTO getByUsername(String username, String sortBy, String direction);
}
