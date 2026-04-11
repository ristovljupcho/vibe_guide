package com.vibe_guide.place.repositories;

import com.vibe_guide.place.entities.Place;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, UUID> {}
