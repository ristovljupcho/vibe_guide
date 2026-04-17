package com.vibe_guide.domain.eventgallery.services;

import com.vibe_guide.domain.eventgallery.entities.EventGallery;
import java.util.List;
import java.util.UUID;

public interface EventGalleryQueryService {
  List<EventGallery> getAllByEventId(UUID eventId);
}

