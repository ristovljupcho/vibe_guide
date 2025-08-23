package com.vibe_guide.repositories;

import com.vibe_guide.entities.Place;
import com.vibe_guide.projections.PlacePreviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {
    @Query(value = """
            SELECT
                p.id AS placeId,
                p.name AS placeName,
                p.description,
                p.address,
                p.rating,
                p.primary_type AS primaryType,
                ARRAY_AGG(t.name ORDER BY pt.like_counter DESC) FILTER (WHERE pt.rn <= 3) AS topTraits
            FROM (
                SELECT 
                    place_id,
                    trait_id,
                    like_counter,
                    ROW_NUMBER() OVER (PARTITION BY place_id ORDER BY like_counter DESC) AS rn
                FROM place_trait
            ) pt
            JOIN place p ON p.id = pt.place_id
            LEFT JOIN trait t ON t.id = pt.trait_id
            WHERE pt.rn <= 3
            GROUP BY p.id, p.name, p.rating, p.primary_type
            ORDER BY p.rating DESC
            """, nativeQuery = true)
    List<PlacePreviewProjection> findTopPlaces();
}