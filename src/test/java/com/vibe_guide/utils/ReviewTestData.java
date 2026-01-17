package com.vibe_guide.utils;

import com.vibe_guide.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.dtos.ReviewPreviewResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.Review;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.ReviewSortBy;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class ReviewTestData {

    public static final UUID REVIEW_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    public static final UUID SECOND_REVIEW_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
    public static final UUID USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID PLACE_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static final String DESCRIPTION = "Amazing place with great vibes!";
    public static final Float RATING = 4.5f;
    public static final LocalDate DATE_CREATED = LocalDate.of(2024, 1, 15);
    public static final LocalDate DATE_MODIFIED = LocalDate.of(2024, 2, 10);

    public static final ReviewSortBy REVIEW_SORT_BY = ReviewSortBy.RATING;
    public static final Sort SORT = Sort.by(Sort.Direction.DESC, "rating");
    public static final PageRequest PAGE_REQUEST =
            PageRequest.of(SharedTestData.PAGE, SharedTestData.SIZE, SORT);

    public static Review getReview() {
        Review review = new Review();
        review.setId(REVIEW_ID);
        review.setDateCreated(DATE_CREATED);
        review.setDateModified(DATE_MODIFIED);
        review.setRating(RATING);
        review.setDescription(DESCRIPTION);
        review.setUser(getUser());
        review.setPlace(getPlace());
        return review;
    }

    public static User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUsername("testuser");
        return user;
    }

    public static Place getPlace() {
        Place place = new Place();
        place.setId(PLACE_ID);
        return place;
    }

    public static List<Review> getReviewList() {
        return List.of(getReview(), getAnotherReview());
    }

    public static Review getAnotherReview() {
        Review review = new Review();
        review.setId(SECOND_REVIEW_ID);
        review.setDateCreated(LocalDate.of(2024, 3, 10));
        review.setDateModified(LocalDate.of(2024, 3, 12));
        review.setRating(3.7f);
        review.setDescription("Nice spot, a bit crowded.");
        review.setUser(getUser());
        review.setPlace(getPlace());
        return review;
    }

    public static Page<Review> getPageReviews() {
        return new PageImpl<>(getReviewList());
    }

    public static ReviewPreviewResponseDTO getReviewPreviewResponseDTO() {
        return new ReviewPreviewResponseDTO(
                "testuser",
                PLACE_ID,
                RATING,
                DATE_CREATED,
                DATE_MODIFIED,
                DESCRIPTION
        );
    }

    public static List<ReviewPreviewResponseDTO> getReviewPreviewResponseDTOs() {
        return List.of(
                getReviewPreviewResponseDTO(),
                new ReviewPreviewResponseDTO(
                        "testuser",
                        PLACE_ID,
                        3.7f,
                        LocalDate.of(2024, 3, 10),
                        LocalDate.of(2024, 3, 12),
                        "Nice spot, a bit crowded."
                )
        );
    }

    public static Page<ReviewPreviewResponseDTO> getPageReviewPreviewResponseDTOs() {
        return new PageImpl<>(getReviewPreviewResponseDTOs());
    }

    public static ReviewInsertRequestDTO getReviewInsertRequestDTO() {
        return new ReviewInsertRequestDTO(
                USER_ID,
                PLACE_ID,
                DESCRIPTION,
                RATING
        );
    }
}
