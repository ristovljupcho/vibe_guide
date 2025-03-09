package com.vibe_guide.specifications;

import com.vibe_guide.entities.Review;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ReviewSpecification {

    /**
     * Returns a Specification that filters reviews by the given place ID.
     * <p> If the provided placeId is null, this specification returns a conjunction,
     * which effectively means "no filtering" on placeId. </p>
     *
     * @param placeId the UUID of the place to filter by; if placeId is null, no filtering is applied.
     * @return a Specification for filtering by placeId or no specification if placeId is null.
     */
    public static Specification<Review> hasPlaceId(UUID placeId) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->{

        if (placeId == null) {
            return cb.conjunction();
        }
        return cb.equal(root.get("user").get("id"), placeId);
    };
    }

    /**
     * Returns a Specification that filters reviews by the given user ID.
     * <p> If the provided userId is null, this specification returns a conjunction,
     * meaning no filtering is applied. </p>
     *
     * @param userId the UUID of the user to filter by; if userId is null, no filtering is applied.
     * @return a Specification for filtering by userId or no specification if userId is null.
     */
    public static Specification<Review> hasUserId(UUID userId) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (userId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("user").get("id"), userId);
        };
    }

    /**
     * <p> If the provided rating is null, this specification returns a conjunction,
     * meaning no filtering is applied. </p>
     *
     * @param rating the minimum rating threshold to filter by; if null, no filtering is applied.
     * @return a Specification for filtering reviews with a rating greater than or equal to the specified value.
     */
    public static Specification<Review> ratingGreaterThanOrEqual(Float rating) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->{
            if(rating == null){
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("rating"), rating);
        };
    }


}


