package com.vibe_guide.repositories;

import com.vibe_guide.entities.PlaceTrait;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.composite_keys.PlaceTraitId;
import com.vibe_guide.entities.views.TraitLikesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaceTraitRepository extends JpaRepository<PlaceTrait, PlaceTraitId> {

    @Query("SELECT pt.trait " +
            "FROM PlaceTrait AS pt " +
            "WHERE pt.place.id = :placeId " +
            "ORDER BY pt.priority DESC, pt.likeCounter DESC")
    List<Trait> getTraitsForPlaceCarousel(UUID placeId);

    @Query("SELECT tls " +
            "FROM TraitLikesSummary AS tls ")
    List<TraitLikesSummary> getTopTraits();

    Optional<PlaceTrait> findByPlaceIdAndTraitId(UUID placeId, UUID traitId);

    @Query("SELECT pt.trait.id " +
            "FROM PlaceTrait AS pt " +
            "WHERE pt.place.id = :placeId")
    List<UUID> findAllTraitIdsByPlaceId(UUID placeId);

    List<PlaceTrait> findAllByPlaceId(UUID placeId);

    List<PlaceTrait> findAllByPlaceIdAndTraitIdIn(UUID placeId, List<UUID> traitIds);
}