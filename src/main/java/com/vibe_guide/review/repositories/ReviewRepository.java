package com.vibe_guide.review.repositories;

import com.vibe_guide.review.entities.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository
    extends JpaRepository<Review, UUID>, JpaSpecificationExecutor<Review> {

  @EntityGraph(attributePaths = {"user", "place"})
  Page<Review> findAllByPlaceIdOrderByDateModifiedDescDateCreatedDesc(
      UUID placeId, Pageable pageable);

  @EntityGraph(attributePaths = {"user", "place"})
  Page<Review> findByPlaceIdOrderByRatingDesc(UUID placeId, Pageable pageable);

  @EntityGraph(attributePaths = {"user", "place"})
  List<Review> findAllByPlaceIdOrderByDateCreatedAsc(UUID placeId);

  @Override
  @EntityGraph(attributePaths = {"user", "place"})
  Page<Review> findAll(Specification<Review> spec, Pageable pageable);

  @EntityGraph(attributePaths = {"user", "place"})
  @Query(
      "SELECT review "
          + "FROM Review review "
          + "JOIN Place place ON review.place.id = place.id "
          + "WHERE place.id = :placeId "
          + "ORDER BY review.rating DESC"
          + " LIMIT 5 ")
  List<Review> getTopFiveByPlaceId(@Param("placeId") UUID placeId);
}
