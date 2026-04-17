package com.vibe_guide.domain.review.services;

import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.domain.review.dtos.ReviewResponseDTO;
import com.vibe_guide.domain.review.dtos.ReviewSearchCriteriaDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ReviewQueryService {

  Page<ReviewResponseDTO> getPaginatedByPlaceId(
      UUID placeId, ReviewSortBy sortBy, SortDirection sortDirection, int page, int size);

  List<ReviewResponseDTO> getAllByPlaceId(UUID placeId);

  Page<ReviewResponseDTO> getByCriteria(
      ReviewSearchCriteriaDTO searchCriteria,
      ReviewSortBy sortBy,
      SortDirection sortDirection,
      int page,
      int size);

  List<ReviewResponseDTO> getTopFiveByPlaceId(UUID placeId);
}

