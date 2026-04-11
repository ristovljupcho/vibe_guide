package com.vibe_guide.review.services;

import com.vibe_guide.review.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.review.dtos.ReviewUpdateRequestDTO;
import java.util.UUID;

public interface ReviewManagementService {
  String insertReview(ReviewInsertRequestDTO reviewInsertRequestDTO);

  String updateReview(ReviewUpdateRequestDTO reviewUpdateRequestDTO);

  String deleteReview(UUID reviewId);
}
