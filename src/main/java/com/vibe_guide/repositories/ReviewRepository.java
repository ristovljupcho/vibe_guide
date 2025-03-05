package com.vibe_guide.repositories;

import com.vibe_guide.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>, JpaSpecificationExecutor<Review> {

    Page<Review> findAllByPlaceIdOrderByDateModifiedDescDateCreatedDesc(UUID placeId, Pageable pageable);

    Page<Review> findByPlaceIdOrderByRatingDesc(UUID placeId, Pageable pageable);

    @Query("SELECT review " +
            "FROM Review review " +
            "JOIN Place place ON review.place.id = place.id " +
            "WHERE place.id = :placeId " +
            "ORDER BY review.rating DESC" +
            " LIMIT 5 ")
    List<Review> getTopFiveReviewsByPlace(@Param("placeId") UUID placeId);

}
