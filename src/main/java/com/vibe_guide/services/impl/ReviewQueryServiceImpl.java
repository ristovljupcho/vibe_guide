package com.vibe_guide.services.impl;

import com.vibe_guide.converters.ReviewConverter;
import com.vibe_guide.dtos.ReviewResponseDTO;
import com.vibe_guide.dtos.ReviewSearchCriteriaDTO;
import com.vibe_guide.entities.Review;
import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.repositories.ReviewRepository;
import com.vibe_guide.services.ReviewQueryService;
import com.vibe_guide.specifications.ReviewSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * @return A {@link Page} containing {@link ReviewResponseDTO} objects.
     */
    @Override
    public Page<ReviewResponseDTO> getPaginatedReviews(UUID placeId, ReviewSortBy sortBy,
                                                       SortDirection sortDirection, int page,
                                                       int size) {
        Pageable pageable = createPageable(sortBy, sortDirection, page, size);
        Page<Review> reviewPage;

        if (sortBy == ReviewSortBy.DATE) {
            reviewPage = reviewRepository.findAllByPlaceIdOrderByDateModifiedDescDateCreatedDesc(placeId, pageable);
        } else {
            reviewPage = reviewRepository.findByPlaceIdOrderByRatingDesc(placeId, pageable);
        }

        return reviewPage.map(reviewConverter::toReviewResponseDTO);
    }

    /**
     * Retrieves list of reviews for a place sorted by dateCreated order in asc order.
     *
     * @param placeId The unique identifier of the place for which reviews are being retrieved.
     * @return List of {@link ReviewResponseDTO}.
     */
    @Override
    public List<ReviewResponseDTO> getReviewsForPlace(UUID placeId) {
        List<Review> reviews = reviewRepository.findAllByPlaceIdOrderByDateCreatedAsc(placeId);

        return reviews.stream().map(reviewConverter::toReviewResponseDTO).toList();
    }

    /**
     * Retrieves a paginated list of {@link ReviewResponseDTO} objects based on the specified search criteria.
     * Supports filtering by place ID, user ID, and minimum rating. Sorting can be done by rating or UUID (default),
     * in ascending (ASC) or descending (DESC) order.
     *
     * @param searchCriteria Contains filtering criteria (such as placeId, userId, and rating).
     * @param sortBy         used for sorting, default review sort criteria is <b><i>TYPE</i></b> from enum
     *                       {@link ReviewSortBy}.
     * @param sortDirection  sortDirection used for sorting direction, default sort direction is <b><i>ASC</i></b>
     *                       from enum
     *                       {@link SortDirection}.
     * @param page           page number.
     * @param size           page size.
     * @return A {@link Page} containing {@link ReviewResponseDTO} objects.
     */
    @Override
    public Page<ReviewResponseDTO> findByReviewCriteria(ReviewSearchCriteriaDTO searchCriteria,
                                                        ReviewSortBy sortBy, SortDirection sortDirection,
                                                        int page, int size) {

        Specification<Review> specification = Specification.where(null);

        UUID placeId = searchCriteria.placeId();
        if (placeId != null) {
            specification = specification.and(ReviewSpecification.hasPlaceId(placeId));
        }
        UUID userId = searchCriteria.userId();
        if (userId != null) {
            specification = specification.and(ReviewSpecification.hasUserId(userId));
        }
        Float rating = searchCriteria.rating();
        if (rating != null) {
            specification = specification.and(ReviewSpecification.ratingGreaterThanOrEqual(rating));
        }

        Pageable pageable = createPageable(sortBy, sortDirection, page, size);

        return this.reviewRepository.findAll(specification, pageable)
                .map(reviewConverter::toReviewResponseDTO);
    }

    /**
     * Retrieves the top five reviews for a given place, sorted by rating in descending order.
     *
     * @param placeId The UUID of the place for which to fetch the top reviews.
     * @return A list of up to five {@link ReviewResponseDTO} objects representing the highest-rated reviews.
     */

    @Override
    public List<ReviewResponseDTO> getTopFiveReviews(UUID placeId) {
        List<Review> topReviews = reviewRepository.getTopFiveReviewsByPlace(placeId);

        return topReviews.stream()
                .map(reviewConverter::toReviewResponseDTO)
                .toList();
    }


    /**
     * Creates a {@link Pageable} object for pagination and sorting.
     *
     * @param sortBy        sortBy used for sorting, default review sort criteria is <b><i>TYPE</i></b> from enum
     *                      {@link ReviewSortBy}.
     * @param sortDirection sortDirection used for sorting direction, default sort direction is <b><i>ASC</i></b>
     *                      from enum
     *                      {@link SortDirection}.
     * @param page          The page number (zero-based) for pagination.
     * @param size          The number of elements per page.
     * @return A {@link Pageable} instance configured with sorting and pagination settings.
     */

    private Pageable createPageable(ReviewSortBy sortBy, SortDirection sortDirection, int page, int size) {
        String sortField = switch (sortBy) {
            case DEFAULT -> "id";
            case RATING -> "rating";
            case DATE -> "dateCreated";
        };

        Sort sort =
                Sort.by(sortDirection == SortDirection.DESC ? Sort.Order.desc(sortField) : Sort.Order.asc(sortField));
        return PageRequest.of(page, size, sort);
    }
}
