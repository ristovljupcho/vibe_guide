package com.vibe_guide.repositories;

import com.vibe_guide.entities.PhotoGalleryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoGalleryEventRepository extends JpaRepository<PhotoGalleryEvent, UUID> {
}
