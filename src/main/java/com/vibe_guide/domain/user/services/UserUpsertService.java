package com.vibe_guide.domain.user.services;

import com.vibe_guide.domain.user.dtos.UserPreviewResponseDTO;
import com.vibe_guide.domain.user.dtos.UserUpsertRequestDTO;

public interface UserUpsertService {
  UserPreviewResponseDTO upsert(UserUpsertRequestDTO dto);
}
