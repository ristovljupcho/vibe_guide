package com.vibe_guide.domain.placegallery.repositories;

import com.vibe_guide.domain.placegallery.entities.PlaceGallery;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceGalleryRepository extends JpaRepository<PlaceGallery, UUID> {
  List<PlaceGallery> findAllByPlaceId(UUID placeId);
}

