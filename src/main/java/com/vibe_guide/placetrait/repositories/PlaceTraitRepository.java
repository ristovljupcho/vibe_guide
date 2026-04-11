package com.vibe_guide.placetrait.repositories;

import com.vibe_guide.placetrait.entities.PlaceTrait;
import com.vibe_guide.trait.entities.Trait;
import com.vibe_guide.traitlike.entities.TraitLikesSummary;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaceTraitRepository extends JpaRepository<PlaceTrait, UUID> {

  @Query(
      "SELECT pt.trait "
          + "FROM PlaceTrait AS pt "
          + "WHERE pt.place.id = :placeId "
          + "ORDER BY pt.priority DESC, pt.likeCounter DESC")
  List<Trait> getTraitsForPlaceCarousel(UUID placeId);

  @Query(
      "SELECT pt.trait "
          + "FROM PlaceTrait AS pt "
          + "WHERE pt.place.id = :placeId "
          + "ORDER BY pt.priority DESC, pt.likeCounter DESC "
          + "LIMIT 5")
  List<Trait> getTopTraitsForPlace(UUID placeId);

  @Query("SELECT tls " + "FROM TraitLikesSummary AS tls ")
  List<TraitLikesSummary> getTopTraits();

  Optional<PlaceTrait> findByPlaceIdAndTraitId(UUID placeId, UUID traitId);

  @Query("SELECT pt.trait.id " + "FROM PlaceTrait AS pt " + "WHERE pt.place.id = :placeId")
  List<UUID> findAllTraitIdsByPlaceId(UUID placeId);

  List<PlaceTrait> findAllByPlaceId(UUID placeId);

  @Query(
      "SELECT t "
          + "FROM Trait t "
          + "LEFT JOIN PlaceTrait pt ON t.id = pt.trait.id AND pt.place.id = :placeId "
          + "WHERE pt.trait.id IS NULL")
  List<Trait> getMissingTraitsForPlace(UUID placeId);

  List<PlaceTrait> findAllByPlaceIdAndTraitIdIn(UUID placeId, List<UUID> traitIds);
}
