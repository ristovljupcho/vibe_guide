package com.vibe_guide.services.impl;

import com.vibe_guide.converters.ReviewConverter;
import com.vibe_guide.dtos.ReviewPreviewResponseDTO;
import com.vibe_guide.dtos.ReviewSearchCriteriaDTO;
import com.vibe_guide.entities.Review;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.repositories.ReviewRepository;
import com.vibe_guide.utils.ReviewTestData;
import com.vibe_guide.utils.SharedTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewQueryServiceImplTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    ReviewConverter reviewConverter;

    @InjectMocks
    ReviewQueryServiceImpl reviewQueryService;

    @Test
    void getPaginatedReviews_sortByRating_returnReviewPreviewResponseDTOs() {
        given(reviewRepository.findByPlaceIdOrderByRatingDesc(
                ReviewTestData.PLACE_ID,
                ReviewTestData.PAGE_REQUEST)
        ).willReturn(ReviewTestData.getPageReviews());

        given(reviewConverter.toReviewPreviewResponseDTO(any(Review.class)))
                .willReturn(ReviewTestData.getReviewPreviewResponseDTO());

        Page<ReviewPreviewResponseDTO> result = reviewQueryService.getPaginatedReviews(
                ReviewTestData.PLACE_ID,
                ReviewTestData.REVIEW_SORT_BY,
                SortDirection.DESC,
                SharedTestData.PAGE,
                SharedTestData.SIZE
        );

        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void getTopFiveReviews_validPlaceId_returnListOfReviewPreviewResponseDTOs() {
        given(reviewRepository.getTopFiveReviewsByPlace(ReviewTestData.PLACE_ID))
                .willReturn(ReviewTestData.getReviewList());

        given(reviewConverter.toReviewPreviewResponseDTO(any(Review.class)))
                .willReturn(ReviewTestData.getReviewPreviewResponseDTO());

        List<ReviewPreviewResponseDTO> result = reviewQueryService.getTopFiveReviews(ReviewTestData.PLACE_ID);

        assertThat(result).hasSize(2);
    }

    @Test
    void findByReviewCriteria_filterByPlaceIdAndRating_returnMatchingResults() {
        ReviewSearchCriteriaDTO criteria = new ReviewSearchCriteriaDTO(
                ReviewTestData.PLACE_ID,
                null,
                ReviewTestData.RATING
        );

        given(reviewRepository.findAll(ArgumentMatchers.<Specification<Review>>any(), any(Pageable.class)))
                .willReturn(ReviewTestData.getPageReviews());

        given(reviewConverter.toReviewPreviewResponseDTO(any(Review.class)))
                .willReturn(ReviewTestData.getReviewPreviewResponseDTO());

        Page<ReviewPreviewResponseDTO> result = reviewQueryService.findByReviewCriteria(
                criteria,
                ReviewTestData.REVIEW_SORT_BY,
                SortDirection.DESC,
                SharedTestData.PAGE,
                SharedTestData.SIZE
        );

        assertThat(result.getContent()).hasSize(2);
    }
}
