package com.vibe_guide.services;

import com.vibe_guide.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.dtos.ReviewUpdateRequestDTO;

import java.util.UUID;

public interface ReviewManagementService {
    String insertReview(ReviewInsertRequestDTO reviewInsertRequestDTO);

    String updateReview(ReviewUpdateRequestDTO reviewUpdateRequestDTO);

    String deleteReview(UUID reviewId);
}
