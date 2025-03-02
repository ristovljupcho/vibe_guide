package com.vibe_guide.repositories;

import com.vibe_guide.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("select review " +
            "from Review review " +
            "join review.place place " +
            "join review.user user" +
            " where place.id = :placeId " +
            " and user.id = :userId" +
            " order by review.rating desc  "
    )
    Page<Review> getPaginatedReviewsByPlaceAndUser(@Param("placeId") UUID placeId,
                                                   @Param("userId") UUID userId,
                                                   Pageable pageable);//for superAdmins

    @Query("select review" +
            " from Review review " +
            "join review.place place" +
            " where place.id = :placeId " +
            " order by review.rating desc ")
    Page<Review> getTopReviewsByPlace(@Param("placeId") UUID placeId, Pageable pageable);

    ;

    @Query("select review " +
            "from Review review " +
            "join review.place place " +
            " where place.id = :placeId " +
            " order by review.rating desc"
    )
    Page<Review> getPaginatedReviewsByPlace(@Param("placeId") UUID placeId,
                                            Pageable pageable);//for users
}
