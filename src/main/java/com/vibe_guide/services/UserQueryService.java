package com.vibe_guide.services;

import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.enums.Role;
import com.vibe_guide.enums.SortDirection;
import com.vibe_guide.enums.UserSortBy;
import org.springframework.data.domain.Page;

public interface UserQueryService {

    Page<UserPreviewResponseDTO> getPaginatedUsers(Role role,
                                                   UserSortBy sortBy,
                                                   SortDirection sortDirection,
                                                   int page,
                                                   int size);
}
