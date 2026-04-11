package com.vibe_guide.review.services;

import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.review.dtos.ReviewResponseDTO;
import com.vibe_guide.review.dtos.ReviewSearchCriteriaDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ReviewQueryService {

  Page<ReviewResponseDTO> getPaginatedReviews(
      UUID placeId, ReviewSortBy sortBy, SortDirection sortDirection, int page, int size);

  List<ReviewResponseDTO> getReviewsForPlace(UUID placeId);

  Page<ReviewResponseDTO> findByReviewCriteria(
      ReviewSearchCriteriaDTO searchCriteria,
      ReviewSortBy sortBy,
      SortDirection sortDirection,
      int page,
      int size);

  List<ReviewResponseDTO> getTopFiveReviews(UUID placeId);
}
