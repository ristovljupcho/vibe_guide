package com.vibe_guide.domain.place.repositories;

import com.vibe_guide.domain.place.entities.Place;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, UUID> {}

