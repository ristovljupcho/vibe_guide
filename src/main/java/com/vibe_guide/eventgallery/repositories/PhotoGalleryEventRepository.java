package com.vibe_guide.eventgallery.repositories;

import com.vibe_guide.eventgallery.entities.EventGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoGalleryEventRepository extends JpaRepository<EventGallery, UUID> {
}
