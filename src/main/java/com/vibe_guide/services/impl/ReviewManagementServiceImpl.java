package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.dtos.ReviewUpdateRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.Review;
import com.vibe_guide.entities.User;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.ReviewNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.ReviewRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.services.ReviewManagementService;
import com.vibe_guide.utils.ReviewResponseMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewManagementServiceImpl implements ReviewManagementService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    /**
     * Inserts a new {@link Review} with provided {@link ReviewInsertRequestDTO}
     * @param reviewInsertRequestDTO DTO used for inserting new {@link Review} for a certain place by existing User
     * @return Response message of type {@link ReviewResponseMessage}
     */
    @Transactional
    @Override
    public String insertReview(ReviewInsertRequestDTO reviewInsertRequestDTO) {

        User user = userRepository.findById(reviewInsertRequestDTO.userId())
                .orElseThrow(() -> new UserNotFoundException(reviewInsertRequestDTO.userId()));
        Place place = placeRepository.findById(reviewInsertRequestDTO.placeId())
                .orElseThrow(() -> new PlaceNotFoundException(reviewInsertRequestDTO.placeId()));


        Review review = new Review();
        review.setUser(user);
        review.setPlace(place);
        review.setRating(reviewInsertRequestDTO.rating());
        review.setDescription(reviewInsertRequestDTO.description());
        reviewRepository.save(review);

        return ReviewResponseMessage.REVIEW_INSERT_MESSAGE;
    }

    /**
     * Updates an existing {@link Review} with new rating and description.
     *
     * @param reviewUpdateRequestDTO the DTO containing the updated review details,
     *                               including the review ID, new rating, and new description.
     * @return a response message of type {@link ReviewResponseMessage} confirming the review update.
     * @throws ReviewNotFoundException if no review with the specified ID is found.
     */
    @Transactional
    @Override
    public String updateReview(ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        UUID reviewId = reviewUpdateRequestDTO.reviewId();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        review.setRating(reviewUpdateRequestDTO.rating());
        review.setDescription(reviewUpdateRequestDTO.description());
        reviewRepository.save(review);

        return ReviewResponseMessage.REVIEW_UPDATE_MESSAGE;
    }


    /**
     * Deletes a {@link Review} object with provided <b><i>UUID reviewId </i></b>
     * @param reviewId id of the {@link Review} object that needs to be deleted
     * @return a response message of type {@link ReviewResponseMessage} confirming the deletion.
     */
    @Transactional
    @Override
    public String deleteReview(UUID reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) {
            throw new ReviewNotFoundException(reviewId);
        }
        reviewRepository.delete(review.get());
        return ReviewResponseMessage.REVIEW_DELETE_MESSAGE;
    }


}
