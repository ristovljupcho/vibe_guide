package com.vibe_guide.review.services;

import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.ReviewNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.review.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.review.dtos.ReviewUpdateRequestDTO;
import com.vibe_guide.review.entities.Review;
import com.vibe_guide.review.repositories.ReviewRepository;
import com.vibe_guide.review.utils.ReviewResponseMessage;
import com.vibe_guide.user.entities.User;
import com.vibe_guide.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewManagementServiceImpl implements ReviewManagementService {

  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final PlaceRepository placeRepository;

  @Transactional
  @Override
  public String insertReview(ReviewInsertRequestDTO reviewInsertRequestDTO) {

    User user =
        userRepository
            .findById(reviewInsertRequestDTO.userId())
            .orElseThrow(() -> new UserNotFoundException(reviewInsertRequestDTO.userId()));
    Place place =
        placeRepository
            .findById(reviewInsertRequestDTO.placeId())
            .orElseThrow(() -> new PlaceNotFoundException(reviewInsertRequestDTO.placeId()));

    Review review = new Review();
    review.setUser(user);
    review.setPlace(place);
    review.setRating(reviewInsertRequestDTO.rating());
    review.setDescription(reviewInsertRequestDTO.description());
    reviewRepository.save(review);

    return ReviewResponseMessage.REVIEW_INSERT_MESSAGE;
  }

  @Transactional
  @Override
  public String updateReview(ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
    UUID reviewId = reviewUpdateRequestDTO.reviewId();

    Review review =
        reviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    review.setRating(reviewUpdateRequestDTO.rating());
    review.setDescription(reviewUpdateRequestDTO.description());
    reviewRepository.save(review);

    return ReviewResponseMessage.REVIEW_UPDATE_MESSAGE;
  }

  @Transactional
  @Override
  public String deleteReview(UUID reviewId) {
    Review review =
        reviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    reviewRepository.delete(review);
    return ReviewResponseMessage.REVIEW_DELETE_MESSAGE;
  }
}
