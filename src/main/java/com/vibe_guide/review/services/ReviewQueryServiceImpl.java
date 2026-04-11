package com.vibe_guide.review.services;

import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.review.dtos.ReviewResponseDTO;
import com.vibe_guide.review.dtos.ReviewSearchCriteriaDTO;
import com.vibe_guide.review.entities.Review;
import com.vibe_guide.review.mappers.ReviewMapper;
import com.vibe_guide.review.repositories.ReviewRepository;
import com.vibe_guide.specifications.ReviewSpecification;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  @Override
  public Page<ReviewResponseDTO> getPaginatedByPlaceId(
      UUID placeId, ReviewSortBy sortBy, SortDirection sortDirection, int page, int size) {
    Pageable pageable = createPageable(sortBy, sortDirection, page, size);
    Page<Review> reviewPage;

    if (sortBy == ReviewSortBy.DATE) {
      reviewPage =
          reviewRepository.findAllByPlaceIdOrderByDateModifiedDescDateCreatedDesc(
              placeId, pageable);
    } else {
      reviewPage = reviewRepository.findByPlaceIdOrderByRatingDesc(placeId, pageable);
    }

    return reviewPage.map(reviewMapper::toReviewResponseDTO);
  }

  @Override
  public List<ReviewResponseDTO> getAllByPlaceId(UUID placeId) {
    List<Review> reviews = reviewRepository.findAllByPlaceIdOrderByDateCreatedAsc(placeId);

    return reviews.stream().map(reviewMapper::toReviewResponseDTO).toList();
  }

  @Override
  public Page<ReviewResponseDTO> getByCriteria(
      ReviewSearchCriteriaDTO searchCriteria,
      ReviewSortBy sortBy,
      SortDirection sortDirection,
      int page,
      int size) {

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

    return this.reviewRepository
        .findAll(specification, pageable)
        .map(reviewMapper::toReviewResponseDTO);
  }

  @Override
  public List<ReviewResponseDTO> getTopFiveByPlaceId(UUID placeId) {
    List<Review> topReviews = reviewRepository.getTopFiveByPlaceId(placeId);

    return topReviews.stream().map(reviewMapper::toReviewResponseDTO).toList();
  }

  private Pageable createPageable(
      ReviewSortBy sortBy, SortDirection sortDirection, int page, int size) {
    String sortField =
        switch (sortBy) {
          case DEFAULT -> "id";
          case RATING -> "rating";
          case DATE -> "dateCreated";
        };

    Sort sort =
        Sort.by(
            sortDirection == SortDirection.DESC
                ? Sort.Order.desc(sortField)
                : Sort.Order.asc(sortField));
    return PageRequest.of(page, size, sort);
  }
}
