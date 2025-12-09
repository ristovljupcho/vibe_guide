package com.vibe_guide.services.impl;

import com.vibe_guide.converters.ReviewConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.ReviewResponseDTO;
import com.vibe_guide.dtos.ReviewSearchCriteriaDTO;
import com.vibe_guide.entities.Review;
import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.repositories.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewQueryServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewConverter reviewConverter;

    @InjectMocks
    private ReviewQueryServiceImpl service;

    @Test
    void getPaginatedReviewsUsesDateQueryWhenRequested() {
        UUID placeId = TestDataFactory.uuid();
        Review review = TestDataFactory.review(TestDataFactory.uuid(), TestDataFactory.user(TestDataFactory.uuid()),
                TestDataFactory.place(TestDataFactory.uuid()));
        ReviewResponseDTO dto = TestDataFactory.reviewResponseDto();
        Page<Review> page = new PageImpl<>(List.of(review));
        when(reviewRepository.findAllByPlaceIdOrderByDateModifiedDescDateCreatedDesc(eq(placeId), any(Pageable.class)))
                .thenReturn(page);
        when(reviewConverter.toReviewResponseDTO(review)).thenReturn(dto);

        Page<ReviewResponseDTO> result = service.getPaginatedReviews(placeId, ReviewSortBy.DATE,
                SortDirection.DESC, 0, 10);

        assertEquals(List.of(dto), result.getContent());
    }

    @Test
    void getPaginatedReviewsDefaultsToRatingQuery() {
        UUID placeId = TestDataFactory.uuid();
        Review review = TestDataFactory.review(TestDataFactory.uuid(), TestDataFactory.user(TestDataFactory.uuid()),
                TestDataFactory.place(TestDataFactory.uuid()));
        ReviewResponseDTO dto = TestDataFactory.reviewResponseDto();
        Page<Review> page = new PageImpl<>(List.of(review));
        when(reviewRepository.findByPlaceIdOrderByRatingDesc(eq(placeId), any(Pageable.class))).thenReturn(page);
        when(reviewConverter.toReviewResponseDTO(review)).thenReturn(dto);

        Page<ReviewResponseDTO> result = service.getPaginatedReviews(placeId, ReviewSortBy.RATING,
                SortDirection.ASC, 0, 5);

        assertEquals(List.of(dto), result.getContent());
    }

    @Test
    void getReviewsForPlaceReturnsMappedDtos() {
        UUID placeId = TestDataFactory.uuid();
        Review review = TestDataFactory.review(TestDataFactory.uuid(), TestDataFactory.user(TestDataFactory.uuid()),
                TestDataFactory.place(TestDataFactory.uuid()));
        ReviewResponseDTO dto = TestDataFactory.reviewResponseDto();
        when(reviewRepository.findAllByPlaceIdOrderByDateCreatedAsc(placeId)).thenReturn(List.of(review));
        when(reviewConverter.toReviewResponseDTO(review)).thenReturn(dto);

        List<ReviewResponseDTO> result = service.getReviewsForPlace(placeId);

        assertEquals(List.of(dto), result);
    }

    @Test
    void findByReviewCriteriaBuildsSpecification() {
        Review review = TestDataFactory.review(TestDataFactory.uuid(), TestDataFactory.user(TestDataFactory.uuid()),
                TestDataFactory.place(TestDataFactory.uuid()));
        ReviewResponseDTO dto = TestDataFactory.reviewResponseDto();
        Page<Review> page = new PageImpl<>(List.of(review));
        when(reviewRepository.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(reviewConverter.toReviewResponseDTO(review)).thenReturn(dto);

        ReviewSearchCriteriaDTO criteria = TestDataFactory.reviewSearchCriteriaDto(TestDataFactory.uuid(),
                TestDataFactory.uuid());
        Page<ReviewResponseDTO> result = service.findByReviewCriteria(criteria, ReviewSortBy.DEFAULT,
                SortDirection.DESC, 0, 10);

        assertEquals(List.of(dto), result.getContent());
    }
}
