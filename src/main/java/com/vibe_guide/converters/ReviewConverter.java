package com.vibe_guide.converters;

import com.vibe_guide.dtos.ReviewPreviewResponseDTO;
import com.vibe_guide.entities.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

/**
 * <ul>
 *     <li>
 *         Performs conversion of object from type {@link Review} to {@link ReviewPreviewResponseDTO}.
 *     </li>
 * </ul>
 */
@Component
public class ReviewConverter {

    /**
     * * Converts a {@link Review} entity to a {@link ReviewPreviewResponseDTO} entity.
     *
     * @param review {@link Review} entity to convert.
     * @return The converted {@link ReviewPreviewResponseDTO} entity.
     */
    public ReviewPreviewResponseDTO toReviewPreviewResponseDTO(Review review) {

        UUID reviewId = review.getId();
        LocalDate dateCreated = review.getDateCreated();
        LocalDate dateModified = review.getDateModified();
        Float rating = review.getRating();
        String description = review.getDescription();
        UUID placeId = review.getPlace().getId();
        String username = review.getUser().getUsername();

        return new ReviewPreviewResponseDTO(reviewId, dateCreated, dateModified, rating, description, placeId,
                username);
    }
}