package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.dtos.ReviewUpdateRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.Review;
import com.vibe_guide.entities.User;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.ReviewRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.utils.ReviewResponseMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewManagementServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private ReviewManagementServiceImpl service;

    @Test
    void insertReviewPersistsEntity() {
        User user = TestDataFactory.user(TestDataFactory.uuid());
        Place place = TestDataFactory.place(TestDataFactory.uuid());
        ReviewInsertRequestDTO request = TestDataFactory.reviewInsertRequestDto(user.getId(), place.getId());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(placeRepository.findById(place.getId())).thenReturn(Optional.of(place));

        String result = service.insertReview(request);

        assertEquals(ReviewResponseMessage.REVIEW_INSERT_MESSAGE, result);
        verify(reviewRepository).save(org.mockito.ArgumentMatchers.any(Review.class));
    }

    @Test
    void updateReviewUpdatesExistingRecord() {
        UUID reviewId = TestDataFactory.uuid();
        Review existing = TestDataFactory.review(reviewId, TestDataFactory.user(TestDataFactory.uuid()),
                TestDataFactory.place(TestDataFactory.uuid()));
        ReviewUpdateRequestDTO request = TestDataFactory.reviewUpdateRequestDto(reviewId);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existing));

        String result = service.updateReview(request);

        assertEquals(ReviewResponseMessage.REVIEW_UPDATE_MESSAGE, result);
        verify(reviewRepository).save(existing);
    }

    @Test
    void deleteReviewRemovesEntity() {
        UUID reviewId = TestDataFactory.uuid();
        Review existing = TestDataFactory.review(reviewId, TestDataFactory.user(TestDataFactory.uuid()),
                TestDataFactory.place(TestDataFactory.uuid()));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existing));

        String result = service.deleteReview(reviewId);

        assertEquals(ReviewResponseMessage.REVIEW_DELETE_MESSAGE, result);
        verify(reviewRepository).delete(existing);
    }
}
