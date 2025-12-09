package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.dtos.ReviewResponseDTO;
import com.vibe_guide.enums.ReviewSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.services.ReviewManagementService;
import com.vibe_guide.services.ReviewQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewQueryService reviewQueryService;

    @Mock
    private ReviewManagementService reviewManagementService;

    @InjectMocks
    private ReviewController controller;

    @Test
    void getPaginatedReviewsReturnsPage() {
        UUID placeId = TestDataFactory.uuid();
        Page<ReviewResponseDTO> page = new PageImpl<>(List.of(TestDataFactory.reviewResponseDto()));
        when(reviewQueryService.getPaginatedReviews(placeId, ReviewSortBy.DEFAULT, SortDirection.ASC, 0, 5))
                .thenReturn(page);

        ResponseEntity<Page<ReviewResponseDTO>> response =
                controller.getPaginatedReviewsForPlace(placeId, ReviewSortBy.DEFAULT, SortDirection.ASC, 0, 5);

        assertEquals(page, response.getBody());
    }

    @Test
    void insertReviewReturnsMessage() {
        ReviewInsertRequestDTO dto = TestDataFactory.reviewInsertRequestDto(TestDataFactory.uuid(),
                TestDataFactory.uuid());
        when(reviewManagementService.insertReview(dto)).thenReturn("created");

        ResponseEntity<String> response = controller.insertReview(dto);

        assertEquals("created", response.getBody());
    }

    @Test
    void deleteReviewReturnsMessage() {
        UUID reviewId = TestDataFactory.uuid();
        when(reviewManagementService.deleteReview(reviewId)).thenReturn("deleted");

        ResponseEntity<String> response = controller.deleteReview(reviewId);

        assertEquals("deleted", response.getBody());
    }
}
