package com.vibe_guide.place.repositories;

import com.vibe_guide.place.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {
}
