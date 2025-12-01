package com.vibe_guide.repositories;

import com.vibe_guide.entities.views.PlaceTopTraits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PlaceTopTraitsRepository extends JpaRepository<PlaceTopTraits, UUID> {
    @Query("SELECT p FROM PlaceTopTraits p " +
            "WHERE (SELECT COUNT(DISTINCT t.id) FROM PlaceTrait pt JOIN pt.trait t " +
            "WHERE pt.place.id = p.id AND t.name IN :traits) = :traitsSize")
    List<PlaceTopTraits> findAllByTraits(List<String> traits, int traitsSize);

    List<PlaceTopTraits> findTop10ByOrderByRatingDesc();
}