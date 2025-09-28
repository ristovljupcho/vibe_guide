package com.vibe_guide.repositories;

import com.vibe_guide.entities.views.PlaceTopTraits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlaceTopTraitsRepository extends JpaRepository<PlaceTopTraits, UUID> {
    @Query("SELECT p FROM PlaceTopTraits p " +
            "WHERE (SELECT COUNT(DISTINCT t.id) FROM PlaceTrait pt JOIN pt.trait t " +
            "       WHERE pt.place.id = p.id AND t.name IN :traits) = :traitsSize")
    Page<PlaceTopTraits> findAllByTraitsPaginated(
            @Param("traits") List<String> traits,
            @Param("traitsSize") int traitsSize,
            Pageable pageable);

    List<PlaceTopTraits> findTop10ByOrderByRatingDesc();
}