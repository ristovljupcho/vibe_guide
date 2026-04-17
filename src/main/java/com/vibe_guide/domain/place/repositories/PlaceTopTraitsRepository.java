package com.vibe_guide.domain.place.repositories;

import com.vibe_guide.domain.place.entities.PlaceTopTraits;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;

public interface PlaceTopTraitsRepository extends JpaRepository<PlaceTopTraits, UUID> {
  @Query(
      "SELECT p FROM PlaceTopTraits p "
          + "WHERE (SELECT COUNT(DISTINCT t.id) FROM PlaceTrait pt JOIN pt.trait t "
          + "WHERE pt.place.id = p.id AND t.name IN :traits) = :traitsSize")
  List<PlaceTopTraits> findAllByTraits(List<String> traits, int traitsSize, Sort sort);

  List<PlaceTopTraits> findTop10ByOrderByRatingDesc();
}


