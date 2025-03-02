package com.vibe_guide.services;

import com.vibe_guide.dtos.ReviewPreviewResponseDTO;
import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.SortDirection;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ReviewQueryService {

    Page<ReviewPreviewResponseDTO> getPaginatedReviews(SortDirection sortDirection,
                                                       UUID placeId,
                                                       ReviewSortBy sortBy,
                                                       int page,
                                                       int size);
}
