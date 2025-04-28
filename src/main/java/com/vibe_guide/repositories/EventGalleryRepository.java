package com.vibe_guide.repositories;

import com.vibe_guide.entities.EventGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EventGalleryRepository extends JpaRepository<EventGallery, UUID> {
    @Query("SELECT eg " +
            "FROM EventGallery AS eg " +
            "WHERE eg.event.id = :eventId")
    List<EventGallery> findAllByEventId(UUID eventId);
}