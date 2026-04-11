package com.vibe_guide.review.services;

import com.vibe_guide.review.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.review.dtos.ReviewUpdateRequestDTO;
import java.util.UUID;

public interface ReviewManagementService {
  String insert(ReviewInsertRequestDTO reviewInsertRequestDTO);

  String update(ReviewUpdateRequestDTO reviewUpdateRequestDTO);

  String delete(UUID reviewId);
}
