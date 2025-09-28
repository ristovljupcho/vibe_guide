package com.vibe_guide.converters;

import com.vibe_guide.dtos.ReviewResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.Review;
import com.vibe_guide.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * <ul>
 * <li>
 * This class is responsible for providing conversion methods from {@link Review} entity
 * to Data Transfer Objects and vice versa.
 * </li>
 * </ul>
 */
@Component
public class ReviewConverter {

    /**
     * * Converts a {@link Review} entity to a {@link ReviewResponseDTO} entity.
     *
     * @param review {@link Review} entity to convert.
     * @return The converted {@link ReviewResponseDTO} entity.
     */
    public ReviewResponseDTO toReviewResponseDTO(Review review) {
        User user = review.getUser();
        String username = user.getUsername();
        Place place = review.getPlace();
        String placeName = place.getName();
        Float rating = review.getRating();
        LocalDate dateCreated = review.getDateCreated();
        LocalDate dateModified = review.getDateModified();
        String description = review.getDescription();

        return new ReviewResponseDTO(username, placeName, rating, dateCreated, dateModified, description);
    }
}