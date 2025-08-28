package com.vibe_guide.services;

import com.vibe_guide.dtos.ReviewResponseDTO;
import com.vibe_guide.dtos.ReviewSearchCriteriaDTO;
import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ReviewQueryService {

    Page<ReviewResponseDTO> getPaginatedReviews(UUID placeId, ReviewSortBy sortBy,
                                                SortDirection sortDirection,
                                                int page, int size);

    List<ReviewResponseDTO> getReviewsForPlace(UUID placeId);

    Page<ReviewResponseDTO> findByReviewCriteria(ReviewSearchCriteriaDTO searchCriteria,
                                                 ReviewSortBy sortBy, SortDirection sortDirection,
                                                 int page, int size);

    List<ReviewResponseDTO> getTopFiveReviews(UUID placeId);
}
