package com.vibe_guide.domain.review.mappers;

import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.review.dtos.ReviewResponseDTO;
import com.vibe_guide.domain.review.entities.Review;
import com.vibe_guide.domain.user.entities.User;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
  public ReviewResponseDTO toReviewResponseDTO(Review review) {
    User user = review.getUser();
    String username = user.getUsername();
    Place place = review.getPlace();
    String placeName = place.getName();
    Float rating = review.getRating();
    LocalDate dateCreated = review.getDateCreated();
    LocalDate dateModified = review.getDateModified();
    String description = review.getDescription();

    return new ReviewResponseDTO(
        username, placeName, rating, dateCreated, dateModified, description);
  }
}

