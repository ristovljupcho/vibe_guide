package com.vibe_guide.repositories;

import com.vibe_guide.entities.PlaceGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlaceGalleryRepository extends JpaRepository<PlaceGallery, UUID> {
    List<PlaceGallery> findAllByPlaceId(UUID placeId);
}