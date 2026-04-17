package com.vibe_guide.domain.review.services;

import com.vibe_guide.domain.review.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.domain.review.dtos.ReviewUpdateRequestDTO;
import java.util.UUID;

public interface ReviewManagementService {
  String insert(ReviewInsertRequestDTO reviewInsertRequestDTO);

  String update(ReviewUpdateRequestDTO reviewUpdateRequestDTO);

  String delete(UUID reviewId);
}

