package com.vibe_guide.services.impl;

import com.vibe_guide.converters.ReviewConverter;
import com.vibe_guide.dtos.ReviewPreviewResponseDTO;
import com.vibe_guide.entities.Review;
import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.SortDirection;
import com.vibe_guide.repositories.ReviewRepository;
import com.vibe_guide.services.ReviewQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    /**
     * Retrieves {@link Review} objects using pagination. Sorting by RATING or DEFAULT (UUID), in ASC or DESC order.
     *
     * @param sortDirection used for sorting direction, default sort direction is <b><i>ASC</i></b> from enum
     *                      {@link SortDirection}.
     * @param placeId       The unique identifier of the place for which reviews are being retrieved.
     * @param sortBy        used for sorting, default review sort criteria is <b><i>TYPE</i></b> from enum
     *                      {@link ReviewSortBy}.
     * @param page          page number.
     * @param size          page size.
     * @return A {@link Page} containing {@link ReviewPreviewResponseDTO} objects.
     */
    @Override
    public Page<ReviewPreviewResponseDTO> getPaginatedReviews(SortDirection sortDirection, UUID placeId,
                                                              ReviewSortBy sortBy,
                                                              int page, int size) {

        String sortField = switch (sortBy) {
            case DEFAULT -> "id";
            case RATING -> "rating";
        };

        Sort sort =
                Sort.by(sortDirection == SortDirection.DESC ? Sort.Order.desc(sortField) : Sort.Order.asc(sortField));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Review> reviewPage;

        reviewPage = reviewRepository.getPaginatedReviewsByPlace(placeId, pageRequest);

        return reviewPage.map(reviewConverter::toReviewPreviewResponseDTO);

    }
}
