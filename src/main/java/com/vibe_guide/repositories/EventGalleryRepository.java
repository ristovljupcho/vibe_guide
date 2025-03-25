package com.vibe_guide.repositories;

import com.vibe_guide.entities.EventGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventGalleryRepository extends JpaRepository<EventGallery, UUID> {
    List<EventGallery> findAllByEventId(UUID eventId);
}
