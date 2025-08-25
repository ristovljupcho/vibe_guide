package com.vibe_guide.services;

import com.vibe_guide.entities.EventGallery;

import java.util.List;
import java.util.UUID;

public interface EventGalleryQueryService {
    List<EventGallery> getEventGallery(UUID eventId);
    EventGallery getGalleryById(UUID imageId);
}