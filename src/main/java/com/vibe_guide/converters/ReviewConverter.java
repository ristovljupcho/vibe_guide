package com.vibe_guide.converters;

import com.vibe_guide.dtos.ReviewPreviewResponseDTO;
import com.vibe_guide.entities.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

/**
 * <ul>
 * <li>
 *This class is responsible for providing conversion methods from {@link Review} entity
 *to Data Transfer Objects and vice versa.
 * </li>
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
        String username = review.getUser().getUsername();
        LocalDate dateCreated = review.getDateCreated();
        LocalDate dateModified = review.getDateModified();
        Float rating = review.getRating();
        String description = review.getDescription();
        UUID placeId = review.getPlace().getId();

        return new ReviewPreviewResponseDTO(username ,placeId, rating, dateCreated, dateModified,
                description);
    }
}