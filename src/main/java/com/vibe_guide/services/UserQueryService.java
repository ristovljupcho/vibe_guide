package com.vibe_guide.services;

import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.UserSortBy;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserQueryService {

    Page<UserPreviewResponseDTO> getPaginatedUsers(UserSortBy sortBy, SortDirection sortDirection, int page, int size);

    UserPreviewResponseDTO getUserById(UUID userId);

    UserPreviewResponseDTO getUserByUsername(String username, String sortBy, String direction);
}
