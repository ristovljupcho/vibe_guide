package com.vibe_guide.eventgallery.repositories;

import com.vibe_guide.eventgallery.entities.EventGallery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoGalleryEventRepository extends JpaRepository<EventGallery, UUID> {}
