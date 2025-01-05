package com.vibe_guide.repositories;

import com.vibe_guide.entities.PlaceTrait;
import com.vibe_guide.entities.composite_keys.PlaceTraitId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceTraitRepository extends JpaRepository<PlaceTrait, PlaceTraitId> {
}
