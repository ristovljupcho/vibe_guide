package com.vibe_guide.repositories;

import com.vibe_guide.entities.PlaceGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceGalleryRepository extends JpaRepository<PlaceGallery, UUID> {
}
