package com.vibe_guide.domain.review.services.impl;

import com.vibe_guide.domain.review.services.ReviewManagementService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.ReviewNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.review.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.domain.review.dtos.ReviewUpdateRequestDTO;
import com.vibe_guide.domain.review.entities.Review;
import com.vibe_guide.domain.review.repositories.ReviewRepository;
import com.vibe_guide.domain.review.utils.ReviewResponseMessage;
import com.vibe_guide.domain.user.entities.User;
import com.vibe_guide.domain.user.repositories.UserRepository;
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
  public String insert(ReviewInsertRequestDTO reviewInsertRequestDTO) {

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
  public String update(ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
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
  public String delete(UUID reviewId) {
    Review review =
        reviewRepository
            .findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    reviewRepository.delete(review);
    return ReviewResponseMessage.REVIEW_DELETE_MESSAGE;
  }
}


